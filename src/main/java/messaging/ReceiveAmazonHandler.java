package messaging;

import com.google.protobuf.Message;
import common.BuilderUtil;
import common.ConstantUtil;
import common.SeqGenerator;
import model.Package;
import model.Truck;
import protocol.AmazonUps;
import protocol.WorldUps;
import service.PackageService;
import service.TruckService;
import service.UserService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ReceiveAmazonHandler implements Runnable {
    TruckService truckService;
    PackageService packageService;
    UserService userService;

    public ReceiveAmazonHandler() {
        truckService = new TruckService();
        packageService = new PackageService();
        userService = new UserService();
    }

    @Override
    public void run() {
        AmazonUps.UAConnect uaConnect = BuilderUtil.buildUAConnect(ConstantUtil.WORLD_ID);
        Server.amazonClient.sendMessage(uaConnect);
        System.out.println(Server.amazonClient.receiveAUConnected());
        while (true) {
            boolean hasCommandContent = false;
            List<Long> ackList = new ArrayList<>();
            AmazonUps.AUCommands auCommands = Server.amazonClient.receiveARequest();
            System.out.println("receive from amazon: " + auCommands);
            // deal with ack of aucommand to ack old uacommand
            for (long ack: auCommands.getAcksList()) {
                if (Server.uaTruckArrivedMap.containsKey(ack)) {
                    //AmazonUps.UATruckArrived uaTruckArrived = Server.uaTruckArrivedMap.get(ack);
                    Server.uaTruckArrivedMap.remove(ack);
                    System.out.println("remove uaTruckArrived from resend");
                } else if (Server.uaTruckDeliverMadeMap.containsKey(ack)) {
                    //AmazonUps.UATruckDeliverMade uaTruckDeliverMade = Server.uaTruckDeliverMadeMap.get(ack);
                    Server.uaTruckDeliverMadeMap.remove(ack);
                    System.out.println("remove uaTruckDeliverMade from resend");
                } else if (Server.uaUpdatePackageStatusMap.containsKey(ack)) {
                    Server.uaUpdatePackageStatusMap.remove(ack);
                    System.out.println("remove uaUpdatePackageStatusMap from resend");
                }
            }
            // deal with new aucommand
            for (AmazonUps.AUCallTruck auCallTruck: auCommands.getCallTruckList()) {
                hasCommandContent = true;
                ackList.add(auCallTruck.getSeqnum());
                Truck truck = truckService.findNearestTruckToPickUp(auCallTruck.getWhX(), auCallTruck.getWhY());
                System.out.println("find truck for pickup: " + truck);
                WorldUps.UGoPickup uGoPickup = BuilderUtil.buildUGoPickup(truck.getTruckId(), auCallTruck.getWhid(), SeqGenerator.incrementAndGet());
                Server.uGoPickupMap.put(uGoPickup.getSeqnum(), uGoPickup);
                truckService.setTruckStatus(uGoPickup.getTruckid(), ConstantUtil.TRUCK_TRAVEL);
                for (AmazonUps.AUProduct auProduct: auCallTruck.getThingsList()) {
                    packageService.insertPackage(auProduct.getId(), auProduct.getDescription(), auProduct.getCount(), truck.getTruckId(),
                            auProduct.getUserid(), auProduct.getDestX(), auProduct.getDestY(), auCallTruck.getWhid(), auProduct.getDestX(), auProduct.getDestY());
                }
            }
            for (AmazonUps.AUTruckGoDeliver auTruckGoDeliver: auCommands.getTruckGoDeliverList()) {
                hasCommandContent = true;
                ackList.add(auTruckGoDeliver.getSeqnum());
                List<WorldUps.UDeliveryLocation> uDeliveryLocationList = new ArrayList<>();
                for (Package pkg: packageService.findPackageByTruck(auTruckGoDeliver.getTruckid())) {
                    uDeliveryLocationList.add(WorldUps.UDeliveryLocation.newBuilder().setPackageid(pkg.getPackageId()).
                            setX(pkg.getDestX()).setY(pkg.getDestY()).build());
                }
                WorldUps.UGoDeliver uGoDeliver = BuilderUtil.buildUGoDeliver(auTruckGoDeliver.getTruckid(), uDeliveryLocationList, SeqGenerator.incrementAndGet());
                Server.uGoDeliverMap.put(uGoDeliver.getSeqnum(), uGoDeliver);
                truckService.setTruckStatus(uGoDeliver.getTruckid(), ConstantUtil.TRUCK_DELIVER);
            }
            for (AmazonUps.AURequestPackageStatus auRequestPackageStatus: auCommands.getRequestPackageStatusList()) {
                hasCommandContent = true;
                ackList.add(auRequestPackageStatus.getSeqnum());
                Package pkg = packageService.findPackage(auRequestPackageStatus.getShipid());
                Truck truck = truckService.findTruckById(pkg.getTruckId());
                AmazonUps.UAUpdatePackageStatus uaUpdatePackageStatus = BuilderUtil.buildUAUpdatePackageStatus(pkg.getPackageId(), truck.getStatus(), truck.getCurrX(), truck.getCurrY(), SeqGenerator.incrementAndGet());
                Server.uaUpdatePackageStatusMap.put(uaUpdatePackageStatus.getSeqnum(), uaUpdatePackageStatus);
            }
            for (AmazonUps.AUTruckGoLoad auTruckGoLoad: auCommands.getLoadingList()) {
                hasCommandContent = true;
                ackList.add(auTruckGoLoad.getSeqnum());
                truckService.setTruckStatus(auTruckGoLoad.getTruckid(), ConstantUtil.TRUCK_LOAD);
            }
            for (AmazonUps.AURequestSendUserInfo auRequestSendUserInfo: auCommands.getUserInfoList()) {
                hasCommandContent = true;
                ackList.add(auRequestSendUserInfo.getSeqnum());
                userService.storeUser(auRequestSendUserInfo.getUserid(), auRequestSendUserInfo.getPassword(), auRequestSendUserInfo.getUsername());
            }
            AmazonUps.UACommands.Builder uaCommandsBuilder = AmazonUps.UACommands.newBuilder();
            // tell amazon the aucommand that has been received
            uaCommandsBuilder.addAllAcks(ackList);
            if (!hasCommandContent) {
                continue;
            }
            System.out.println("send to amazon: " + uaCommandsBuilder);
            Server.amazonClient.sendMessage(uaCommandsBuilder.build());
        }
    }
}
