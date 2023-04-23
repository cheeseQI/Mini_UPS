package messaging;

import common.BuilderUtil;
import common.SeqGenerator;
import model.Truck;
import protocol.WorldUps;
import service.TruckService;

import java.io.*;
import java.util.concurrent.*;

public class WorldClient extends SocketClient {
    TruckService truckService;
    public WorldClient(String host, int port) {
        super(host, port);
        truckService = new TruckService();
    }

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

//
//    public void updateData(WorldUps.UResponses uResponses) {
//        if (uResponses.getCompletionsCount() > 0) {
//            //todo: change database status
//        }
//        if (uResponses.getDeliveredCount() > 0) {
//            //todo: change database status
//        }
//    }
}

