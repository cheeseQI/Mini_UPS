package messaging;

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
        }
    }

    private void resendToWorld() {
        WorldUps.UCommands.Builder uCommandsBuilder = WorldUps.UCommands.newBuilder();
        if (Server.uGoPickupMap.isEmpty() && Server.uGoDeliverMap.isEmpty()) {
            return;
        }
        for (WorldUps.UGoPickup uGoPickup: Server.uGoPickupMap.values()) {
            uCommandsBuilder.addPickups(uGoPickup);
        }
        for (WorldUps.UGoDeliver uGoDeliver: Server.uGoDeliverMap.values()) {
            uCommandsBuilder.addDeliveries(uGoDeliver);
        }
        Server.worldClient.sendUCommands(uCommandsBuilder.build());
        //todo: add other message
        // todo: need to do database operation after recv acks
    }
}
