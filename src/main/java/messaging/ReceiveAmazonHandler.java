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
            // todo: deal with ack
            // deal with ucommand
            for (AmazonUps.AUCallTruck auCallTruck: auCommands.getCallTruckList()) {
                Truck truck = truckService.findTruckToPickUp();
                System.out.println(truck);
                WorldUps.UGoPickup uGoPickup = BuilderUtil.buildUGoPickup(truck.getTruckId(), auCallTruck.getWhid(), SeqGenerator.incrementAndGet());
                Server.uGoPickupMap.put(uGoPickup.getSeqnum(), uGoPickup);
            }
            for (AmazonUps.AUTruckGoDeliver auTruckGoDeliver: auCommands.getTruckGoDeliverList()) {
                for (AmazonUps.AUDeliveryLocation auDeliveryLocation: auTruckGoDeliver.getPackagesList()) {
                    WorldUps.UGoDeliver uGoDeliver = BuilderUtil.buildUGoDeliver(auTruckGoDeliver.getTruckid(),
                            BuilderUtil.buildUDeliveryLocation(auDeliveryLocation.getShipid(), auDeliveryLocation.getX(), auDeliveryLocation.getY()),
                            SeqGenerator.incrementAndGet());
                    Server.uGoDeliverMap.put(uGoDeliver.getSeqnum(), uGoDeliver);
                }
            }
            //todo: other message like AURequestPackageStatus
            AmazonUps.UACommands.Builder uaCommands = AmazonUps.UACommands.newBuilder();
            uaCommands.addAllAcks(auCommands.getAcksList());
            Server.amazonClient.sendMessage(uaCommands.build());
        }
    }
}
