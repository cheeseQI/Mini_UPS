package controller;

import common.BuilderUtil;
import common.ConstantUtil;
import messaging.WorldClient;
import model.Truck;
import protocol.WorldUps;
import service.TruckService;

import java.util.ArrayList;
import java.util.List;

//todo: this will be multi-thread sending out information
public class TruckController {
    private WorldClient worldClient;
    TruckService truckService;

    public TruckController(String host, int port) {
        worldClient = new WorldClient(host, port);
        worldClient.connect();
        System.out.println("connect to world server successfully!");
        truckService = new TruckService();
    }
    //todo: may need align passing a world id
    public void connectAndInit() {
        //List<WorldUps.UInitTruck> uInitTruckList = truckService.make100Trucks();
        List<WorldUps.UInitTruck> uInitTruckList = new ArrayList<>();
        //uInitTruckList.add(BuilderUtil.buildUInitTruck(1,1,1));
        WorldUps.UConnect uConnect = BuilderUtil.buildUConnect(uInitTruckList);
        worldClient.sendMessage(uConnect);
        WorldUps.UConnected uConnected = worldClient.receiveUConnected();
        System.out.println(uConnected.toString());
        if (!uConnected.getResult().equals(ConstantUtil.CONNECT_SUCCESS)) {
            System.out.println(uConnected.getResult());
            return;
        }
        truckService.storeTrucks(uInitTruckList);
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

    public void goDeliver(int truckId, int x, int y, int packageId) {
        worldClient.sendUGoDeliver(truckId,  x, y, packageId);
        System.out.println(worldClient.receiveUResponse());
        //todo: change database
    }

    public void close() {
        worldClient.disconnect();
    }
}
