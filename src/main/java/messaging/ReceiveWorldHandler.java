package messaging;

import com.google.protobuf.Message;
import common.BuilderUtil;
import common.ConstantUtil;
import common.SeqGenerator;
import model.Truck;
import protocol.AmazonUps;
import protocol.WorldUps;
import service.TruckService;

import java.util.ArrayList;
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
            boolean hasCommandContent = false;
            List<Long> ackList = new ArrayList<>();
            WorldUps.UResponses uResponses = Server.worldClient.receiveUResponse();
            System.out.println("receive uresponse from world: " + uResponses);
            // deal with ack of uResponse, which is used to delete resend uCommands
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
                hasCommandContent = true;
                //todo: change builder
                ackList.add(uFinished.getSeqnum());
                AmazonUps.UATruckArrived uaTruckArrived = BuilderUtil.buildUATruckArrived(uFinished.getTruckid(), uFinished.getX(), uFinished.getY(), SeqGenerator.incrementAndGet());
                Server.uaTruckArrivedMap.put(uaTruckArrived.getSeqnum(), uaTruckArrived);
                System.out.println("store uaTruckArrived");
            }
            for (WorldUps.UDeliveryMade uDeliveryMade: uResponses.getDeliveredList()) {
                hasCommandContent = true;
                ackList.add(uDeliveryMade.getSeqnum());
                AmazonUps.UATruckDeliverMade uaTruckDeliverMade = BuilderUtil.buildUATruckDeliverMade(uDeliveryMade.getTruckid(), uDeliveryMade.getPackageid(), SeqGenerator.incrementAndGet());
                Server.uaTruckDeliverMadeMap.put(uaTruckDeliverMade.getSeqnum(), uaTruckDeliverMade);
                System.out.println("store uaTruckDeliverMade");
            }
            WorldUps.UCommands.Builder  uCommandsBuilder = WorldUps.UCommands.newBuilder();
            // tell world the uresponse that has been received
            uCommandsBuilder.addAllAcks(ackList);
            if (!hasCommandContent) {
                continue;
            }
            System.out.println("send to world: " + uCommandsBuilder);
            Server.worldClient.sendMessage(uCommandsBuilder.build());
        }
    }
}
