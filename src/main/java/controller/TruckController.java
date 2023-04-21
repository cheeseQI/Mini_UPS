package controller;

import common.BuilderUtil;
import common.ConstantUtil;
import messaging.WorldClient;
import model.Truck;
import protocol.WorldUps;
import service.TruckService;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

//todo: this will be multi-thread sending out information
public class TruckController {
    private WorldClient worldClient;
    TruckService truckService;

    public TruckController(String host, int port) {
        worldClient = new WorldClient(host, port);
        System.out.println("connect to world server successfully!");
        truckService = new TruckService();
    }


    public void pickUp(int whid) {
        Truck truck = truckService.findTruckToPickUp();
        System.out.println(truck);
        //long seqNum = SeqGenerator.incrementAndGet();
        worldClient.sendUGoPickup(truck.getTruckId(), whid);
        WorldUps.UResponses uResponses = worldClient.receiveUResponse();
        System.out.println(uResponses);
        if (uResponses.getCompletionsCount() < 1) {
            System.out.println("already response for pickup");
            return;
        }
        WorldUps.UFinished completion = uResponses.getCompletions(0);
        //todo: change database
    }

    public void goDeliver(int truckId, int x, int y, long packageId) {
        worldClient.sendUGoDeliver(truckId,  x, y, packageId);
        System.out.println(worldClient.receiveUResponse());
        //todo: change database
    }

    public void query() {

    }

    public void close() {
        worldClient.disconnect();
    }
}
