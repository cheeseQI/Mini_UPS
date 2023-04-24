package messaging;

import com.google.protobuf.Message;
import common.BuilderUtil;
import common.ConstantUtil;
import common.SeqGenerator;
import model.Package;
import model.Truck;
import org.checkerframework.checker.units.qual.A;
import protocol.AmazonUps;
import protocol.WorldUps;
import service.PackageService;
import service.TruckService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ReceiveAmazonHandler implements Runnable {
    TruckService truckService;
    PackageService packageService;

    public ReceiveAmazonHandler() {
        truckService = new TruckService();
        packageService = new PackageService();
    }

    @Override
    public void run() {
        AmazonUps.UAConnect uaConnect = BuilderUtil.buildUAConnect(ConstantUtil.WORLD_ID);
        Server.amazonClient.sendMessage(uaConnect);
        System.out.println(Server.amazonClient.receiveAUConnected()); //todo: may failed
        while (true) {
            boolean hasCommandContent = false;
            List<Long> ackList = new ArrayList<>();
            AmazonUps.AUCommands auCommands = Server.amazonClient.receiveARequest();
            System.out.println("receive from amazon: " + auCommands);
            // deal with ack of aucommand, which is used to delete resend uaCommands
            for (long ack: auCommands.getAcksList()) {
                if (Server.uaTruckArrivedMap.containsKey(ack)) {
                    AmazonUps.UATruckArrived uaTruckArrived = Server.uaTruckArrivedMap.get(ack);
                    Server.uaTruckArrivedMap.remove(ack);
                    System.out.println("remove uaTruckArrived from resend");
                } else if (Server.uaTruckDeliverMadeMap.containsKey(ack)) {
                    AmazonUps.UATruckDeliverMade uaTruckDeliverMade = Server.uaTruckDeliverMadeMap.get(ack);
                    Server.uaTruckDeliverMadeMap.remove(ack);
                    System.out.println("remove uaTruckDeliverMade from resend");
                }
            }

            for (AmazonUps.AUCallTruck auCallTruck: auCommands.getCallTruckList()) {
                hasCommandContent = true;
                ackList.add(auCallTruck.getSeqnum());
                Truck truck = truckService.findTruckToPickUp();
                System.out.println("find truck for pickup: " + truck);
                WorldUps.UGoPickup uGoPickup = BuilderUtil.buildUGoPickup(truck.getTruckId(), auCallTruck.getWhid(), SeqGenerator.incrementAndGet());
                Server.uGoPickupMap.put(uGoPickup.getSeqnum(), uGoPickup);
                truckService.setTruckStatus(uGoPickup.getTruckid(), ConstantUtil.TRUCK_TRAVEL);
            }
            for (AmazonUps.AUTruckGoDeliver auTruckGoDeliver: auCommands.getTruckGoDeliverList()) {
                hasCommandContent = true;
                ackList.add(auTruckGoDeliver.getSeqnum());
                List<WorldUps.UDeliveryLocation> uDeliveryLocationList = new ArrayList<>();
                WorldUps.UGoDeliver uGoDeliver = BuilderUtil.buildUGoDeliver(auTruckGoDeliver.getTruckid(), uDeliveryLocationList, SeqGenerator.incrementAndGet());
                Server.uGoDeliverMap.put(uGoDeliver.getSeqnum(), uGoDeliver);
                truckService.setTruckStatus(uGoDeliver.getTruckid(), ConstantUtil.TRUCK_DELIVER);
                //todo: also insert pkg into package table
            }
            for (AmazonUps.AURequestPackageStatus auRequestPackageStatus: auCommands.getRequestPackageStatusList()) {
                hasCommandContent = true;
                ackList.add(auRequestPackageStatus.getSeqnum());
                Package pkg = packageService.findPackage(auRequestPackageStatus.getShipid());
//                int[] currLoc = truckService.findTruckById(pkg.getTruckId());
                //todo: update info: builder not desx but currx, need to know package idle or on truck...; design package status
                AmazonUps.UAUpdatePackageStatus uaUpdatePackageStatus = BuilderUtil.buildUAUpdatePackageStatus(pkg.getPackageId(), pkg.getDestX(), pkg.getDestY(), pkg.getStatus(), SeqGenerator.incrementAndGet());
                Server.uaUpdatePackageStatusMap.put(uaUpdatePackageStatus.getSeqnum(), uaUpdatePackageStatus);
            }
            //todo: other message like AURequestPackageStatus
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
