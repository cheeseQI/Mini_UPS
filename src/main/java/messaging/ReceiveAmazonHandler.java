package messaging;

import com.google.protobuf.Message;
import common.BuilderUtil;
import common.ConstantUtil;
import common.SeqGenerator;
import model.Truck;
import org.checkerframework.checker.units.qual.A;
import protocol.AmazonUps;
import protocol.WorldUps;
import service.TruckService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class ReceiveAmazonHandler implements Runnable {
    TruckService truckService;

    public ReceiveAmazonHandler() {
        truckService = new TruckService();
    }

    @Override
    public void run() {
        AmazonUps.UAConnect uaConnect = BuilderUtil.buildUAConnect(ConstantUtil.WORLD_ID);
        Server.amazonClient.sendMessage(uaConnect);
        System.out.println(Server.amazonClient.receiveAUConnected()); //todo: may failed
        while (true) {
            AmazonUps.AUCommands auCommands = Server.amazonClient.receiveARequest();
            // deal with ack past uaCommands
            for (long ack: auCommands.getAcksList()) {
                if (Server.uaTruckArrivedMap.containsKey(ack)) {
                    AmazonUps.UATruckArrived uaTruckArrived = Server.uaTruckArrivedMap.get(ack);
                    Server.uaTruckArrivedMap.remove(ack);
                    System.out.println("do database ops related to uaTruckArrived: " + uaTruckArrived);
                    // todo: database here
                } else if (Server.uaTruckDeliverMadeMap.containsKey(ack)) {
                    AmazonUps.UATruckDeliverMade uaTruckDeliverMade = Server.uaTruckDeliverMadeMap.get(ack);
                    Server.uaTruckDeliverMadeMap.remove(ack);
                    System.out.println("do database ops related to uaTruckDeliverMade: " + uaTruckDeliverMade);
                    //todo: database here
                }
            }
            // deal with uCommand
            for (AmazonUps.AUCallTruck auCallTruck: auCommands.getCallTruckList()) {
                Truck truck = truckService.findTruckToPickUp();
                System.out.println("find truck for pickup: " + truck);
                WorldUps.UGoPickup uGoPickup = BuilderUtil.buildUGoPickup(truck.getTruckId(), auCallTruck.getWhid(), SeqGenerator.incrementAndGet());
                Server.uGoPickupMap.put(uGoPickup.getSeqnum(), uGoPickup);
                System.out.println("store uGoPickup");
            }
            for (AmazonUps.AUTruckGoDeliver auTruckGoDeliver: auCommands.getTruckGoDeliverList()) {
                for (AmazonUps.AUDeliveryLocation auDeliveryLocation: auTruckGoDeliver.getPackagesList()) {
                    WorldUps.UGoDeliver uGoDeliver = BuilderUtil.buildUGoDeliver(auTruckGoDeliver.getTruckid(),
                            BuilderUtil.buildUDeliveryLocation(auDeliveryLocation.getShipid(), auDeliveryLocation.getX(), auDeliveryLocation.getY()),
                            SeqGenerator.incrementAndGet());
                    Server.uGoDeliverMap.put(uGoDeliver.getSeqnum(), uGoDeliver);
                    System.out.println("store uGoDeliver");
                }
            }
            //todo: other message like AURequestPackageStatus
            AmazonUps.UACommands.Builder uaCommandsBuilder = AmazonUps.UACommands.newBuilder();
            uaCommandsBuilder.addAllAcks(auCommands.getAcksList());
            Server.amazonClient.sendMessage(uaCommandsBuilder.build());
        }
    }
}
