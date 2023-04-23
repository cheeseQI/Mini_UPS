package messaging;

import protocol.AmazonUps;
import protocol.WorldUps;

public class ResendHandler implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resendToWorld();
            resendToAmazon();
        }
    }

    private void resendToAmazon() {
        AmazonUps.UACommands.Builder uaCommandBuilder = AmazonUps.UACommands.newBuilder();
        if (Server.uaTruckArrivedMap.isEmpty() && Server.uaTruckDeliverMadeMap.isEmpty()) {// todo: other
            return;
        }
        for (AmazonUps.UATruckArrived uaTruckArrived: Server.uaTruckArrivedMap.values()) {
            uaCommandBuilder.addTruckArrived(uaTruckArrived);
        }
        for (AmazonUps.UATruckDeliverMade uaTruckDeliverMade: Server.uaTruckDeliverMadeMap.values()) {
            uaCommandBuilder.addDelivered(uaTruckDeliverMade);
        }
        System.out.println("send uacommand to amazon: " + uaCommandBuilder);
        Server.amazonClient.sendMessage(uaCommandBuilder.build());
    }

    private void resendToWorld() {
        WorldUps.UCommands.Builder uCommandsBuilder = WorldUps.UCommands.newBuilder();
        if (Server.uGoPickupMap.isEmpty() && Server.uGoDeliverMap.isEmpty()) { // todo: other
            return;
        }
        for (WorldUps.UGoPickup uGoPickup: Server.uGoPickupMap.values()) {
            uCommandsBuilder.addPickups(uGoPickup);
        }
        for (WorldUps.UGoDeliver uGoDeliver: Server.uGoDeliverMap.values()) {
            uCommandsBuilder.addDeliveries(uGoDeliver);
        }
        System.out.println("send ucommand to world: " + uCommandsBuilder);
        Server.worldClient.sendMessage(uCommandsBuilder.build());
        //todo: add other message
        // todo: need to do database operation after recv acks
    }
}
