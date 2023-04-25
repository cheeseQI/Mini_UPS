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
        AmazonUps.AUProduct.Builder auproductBuilder = AmazonUps.AUProduct.newBuilder();
        auproductBuilder.setDescription("cool apple").setId(1).setCount(2).setDestX(11).setDestY(12).setUserid(747);
        AmazonUps.AUCallTruck.Builder auCallTruckBuilder = AmazonUps.AUCallTruck.newBuilder();
        auCallTruckBuilder.setSeqnum(1).setWhid(1).setX(1).setY(1).setShipid(1).addThings(auproductBuilder);
        AmazonUps.AUCommands.Builder auCommand0 = AmazonUps.AUCommands.newBuilder();
        auCommand0.addCallTruck(auCallTruckBuilder.build());
        upsClient.sendMessage(auCommand0.build());
        AmazonUps.UACommands uaCommands = upsClient.receiveUACommands();
        System.out.println("receive call truck back uaCommand ack: " + uaCommands);
        // 3 truck arrived
        AmazonUps.UACommands uaCommands1 = upsClient.receiveUACommands();
        System.out.println("receive truck arrived: " + uaCommands1);
        AmazonUps.AUCommands.Builder auCommandsBuilder1 = AmazonUps.AUCommands.newBuilder();
        auCommandsBuilder1.addAcks(uaCommands1.getTruckArrived(0).getSeqnum());
        System.out.println("send to ups: " + auCommandsBuilder1);
        upsClient.sendMessage(auCommandsBuilder1.build());
        //4 goload
        AmazonUps.AUCommands.Builder auCommandsBuilder2 = AmazonUps.AUCommands.newBuilder();
        auCommandsBuilder2.addLoading(AmazonUps.AUTruckGoLoad.newBuilder().setTruckid(4).setShipid(1).setSeqnum(2));
        System.out.println("send to ups: " + auCommandsBuilder2);
        upsClient.sendMessage(auCommandsBuilder2.build());
        AmazonUps.UACommands uaCommands2 = upsClient.receiveUACommands();
        System.out.println(uaCommands2);
        //5 deliever...
//        upsClient.sendMessage(auCommandsBuilder1.build());
//        AmazonUps.AUCommands.Builder auCommandsBuilder2 = AmazonUps.AUCommands.newBuilder();
//        AmazonUps.AUTruckGoDeliver.Builder auTruckGoDeliverBuilder = AmazonUps.AUTruckGoDeliver.newBuilder();
//        AmazonUps.AUDeliveryLocation.Builder packages = AmazonUps.AUDeliveryLocation.newBuilder();
//        packages.setShipid(1).setX(1).setY(1);
//        auTruckGoDeliverBuilder.setSeqnum(2).setTruckid(uaCommands1.getTruckArrived(0).getTruckid()).setPackages();
//        auCommandsBuilder2.addTruckGoDeliver(auTruckGoDeliverBuilder);
        upsClient.disconnect();
//        while (true) {
//
//        }
    }
}
