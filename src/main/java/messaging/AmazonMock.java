package messaging;

import protocol.AmazonUps;

import java.io.IOException;
import java.net.Socket;

public class AmazonMock {
    public static void main(String[] args) throws IOException {
        UpsClient upsClient = new UpsClient("127.0.0.1", 7474);
        System.out.println("socket with ups");
        AmazonUps.UAConnect uaConnect = upsClient.receiveUAConnect();
        System.out.println(uaConnect);
        // 1 connect
        AmazonUps.AUConnected.Builder builder = AmazonUps.AUConnected.newBuilder();
        builder.setWorldid(uaConnect.getWorldid()).setResult("success!");
        upsClient.sendMessage(builder.build());
        // 2 call truck
        AmazonUps.AUCallTruck.Builder auCallTruckBuilder = AmazonUps.AUCallTruck.newBuilder();
        auCallTruckBuilder.setSeqnum(1).setWhid(1).setX(1).setY(1).setShipid(1);
        AmazonUps.AUCommands.Builder auCommand0 = AmazonUps.AUCommands.newBuilder();
        auCommand0.addCallTruck(auCallTruckBuilder.build());
        upsClient.sendMessage(auCommand0.build());
        System.out.println("wait for response");
        //...startload, update pack
        //...finishload. update pack
        //deliever:
        AmazonUps.UACommands uaCommands = upsClient.receiveUACommands();
        System.out.println("receive call truck back uaCommand ack: " + uaCommands);
        AmazonUps.UACommands uaCommands1 = upsClient.receiveUACommands();
        System.out.println("receive truck arrived: " + uaCommands1);
        AmazonUps.AUCommands.Builder auCommandsBuilder = AmazonUps.AUCommands.newBuilder();
        auCommandsBuilder.addAcks(uaCommands1.getTruckArrived(0).getSeqnum());
        System.out.println("send to ups: " + auCommandsBuilder);
        upsClient.sendMessage(auCommandsBuilder.build());

        while (true) {

        }
    }
}
