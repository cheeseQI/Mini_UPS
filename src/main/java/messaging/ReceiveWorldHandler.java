package messaging;

import com.google.protobuf.Message;
import common.BuilderUtil;
import common.ConstantUtil;
import common.SeqGenerator;
import model.Truck;
import protocol.AmazonUps;
import protocol.WorldUps;
import service.TruckService;

import java.util.List;

public class ReceiveWorldHandler implements Runnable{

    @Override
    public void run() {
        TruckService truckService = new TruckService();
        List<WorldUps.UInitTruck> uInitTruckList = truckService.make100Trucks();
        WorldUps.UConnect uConnect = BuilderUtil.buildUConnect(uInitTruckList);
        Server.worldClient.sendMessage(uConnect);
        WorldUps.UConnected uConnected = Server.worldClient.receiveUConnected();
        System.out.println(uConnected.toString());
        if (!uConnected.getResult().equals(ConstantUtil.CONNECT_SUCCESS)) {
            throw new IllegalArgumentException(uConnected.getResult());
        }
        ConstantUtil.WORLD_ID = uConnected.getWorldid();
        //todo: truckService.storeTrucks(uInitTruckList);
        while (true) {
            WorldUps.UResponses uResponses = Server.worldClient.receiveUResponse();
            // deal with ack past ucommands
            System.out.println("receive response from world: " + uResponses);
            for (long ack: uResponses.getAcksList()) {
                if (Server.uGoPickupMap.containsKey(ack)) {
                    WorldUps.UGoPickup uGoPickup = Server.uGoPickupMap.get(ack);
                    Server.uGoPickupMap.remove(ack);
                    System.out.println("remove uGoPickUp with ack/seq: " + ack);
                    // todo: database here
                } else if (Server.uGoDeliverMap.containsKey(ack)) {
                    WorldUps.UGoDeliver uGoDeliver = Server.uGoDeliverMap.get(ack);
                    Server.uGoDeliverMap.remove(ack);
                    System.out.println("remove uGoDeliver with ack/seq: " + ack);
                    //todo: database here
                }
            }
            for (WorldUps.UFinished uFinished: uResponses.getCompletionsList()) {
                //todo: change builder
                AmazonUps.UATruckArrived uaTruckArrived = BuilderUtil.buildUATruckArrived(uFinished.getTruckid(), uFinished.getX(), uFinished.getY(), SeqGenerator.incrementAndGet());
                Server.uaTruckArrivedMap.put(uaTruckArrived.getSeqnum(), uaTruckArrived);
            }
            for (WorldUps.UDeliveryMade uDeliveryMade: uResponses.getDeliveredList()) {
                AmazonUps.UATruckDeliverMade uaTruckDeliverMade = BuilderUtil.buildUATruckDeliverMade(uDeliveryMade.getTruckid(), uDeliveryMade.getPackageid(), SeqGenerator.incrementAndGet());
                Server.uaTruckDeliverMadeMap.put(uaTruckDeliverMade.getSeqnum(), uaTruckDeliverMade);
            }
            WorldUps.UCommands.Builder  uCommandsBuilder = WorldUps.UCommands.newBuilder();
            uCommandsBuilder.addAllAcks(uResponses.getAcksList());
            Server.amazonClient.sendMessage(uCommandsBuilder.build());
        }
    }
}
