package messaging;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import common.BuilderUtil;
import common.SeqGenerator;
import protocol.WorldUps;

import java.io.*;
import java.net.Socket;

public class WorldClient extends SocketClient {

    public WorldClient(String host, int port) {
        super(host, port);
    }


    //todo: add receiveUAConnected


    public WorldUps.UConnected receiveUConnected() {
        try {
            return WorldUps.UConnected.parseFrom(codedInputStream.readByteArray());
        } catch (IOException e) {
            System.err.println("Error receiving message from " + host + ":" + port);
            e.printStackTrace();
            return null;
        }
    }

    //todo: resend function for <seq, ugopickup>, <seq, ugodeliver> ...
    public WorldUps.UResponses receiveUResponse() {
        try {
            return WorldUps.UResponses.parseFrom(codedInputStream.readByteArray());
        } catch (IOException e) {
            System.err.println("Error receiving message from " + host + ":" + port);
            e.printStackTrace();
            return null;
        }
    }

    public void sendUGoPickup(int truckId, int whid) {
        WorldUps.UGoPickup uGoPickup = BuilderUtil.buildUGoPickup(truckId, whid, SeqGenerator.incrementAndGet());
        WorldUps.UCommands.Builder uCommandsBuilder = WorldUps.UCommands.newBuilder();
        uCommandsBuilder.addPickups(uGoPickup);
        sendMessage(uCommandsBuilder.build());
    }

    public void sendUGoDeliver(int truckId, int x, int y, long packageId) {
        WorldUps.UGoDeliver uGoDeliver = BuilderUtil.buildUGoDeliver(truckId, BuilderUtil.buildUDeliveryLocation(packageId, x, y), SeqGenerator.incrementAndGet());
        WorldUps.UCommands.Builder uCommandsBuilder = WorldUps.UCommands.newBuilder();
        uCommandsBuilder.addDeliveries(uGoDeliver);
        sendMessage(uCommandsBuilder.build());
    }

}

