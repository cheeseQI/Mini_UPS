package messaging;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import common.BuilderUtil;
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

    public void SendUGoPickup(int truckid, int whid, long seqNum) {
        WorldUps.UGoPickup uGoPickup = BuilderUtil.buildUGoPickup(truckid, whid, seqNum);
        WorldUps.UCommands.Builder uCommandsBuilder = WorldUps.UCommands.newBuilder();
        uCommandsBuilder.addPickups(uGoPickup);
        sendMessage(uCommandsBuilder.build());
    }

    public void UGoDeliver(){}
}

