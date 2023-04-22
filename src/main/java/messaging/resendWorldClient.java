package messaging;

import protocol.WorldUps;

public class resendWorldClient implements Runnable{
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
        for (long seq: ServerUA.uwCommandsMap.keySet()) {
            WorldUps.UCommands uCommands = ServerUA.uwCommandsMap.get(seq);
            WorldClient worldClient = new WorldClient("127.0.0.1", 12345);
            worldClient.sendUCommands(uCommands);
        }
    }
}
