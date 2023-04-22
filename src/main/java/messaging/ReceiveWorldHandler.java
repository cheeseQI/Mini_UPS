package messaging;

import common.BuilderUtil;
import common.ConstantUtil;
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
            for (long ack: uResponses.getAcksList()) {
                if (Server.uGoPickupMap.containsKey(ack)) {
                    WorldUps.UGoPickup uGoPickup = Server.uGoPickupMap.get(ack);
                    Server.uGoPickupMap.remove(ack);
                    // todo: database here
                } else if (Server.uGoDeliverMap.containsKey(ack)) {
                    WorldUps.UGoDeliver uGoDeliver = Server.uGoDeliverMap.get(ack);
                    Server.uGoDeliverMap.remove(ack);
                    //todo: database here
                }
            }
            for (WorldUps.UFinished uFinished: uResponses.getCompletionsList()) {

            }
            for (WorldUps.UDeliveryMade uDeliveryMade: uResponses.getDeliveredList()) {

            }
        }
    }
}
