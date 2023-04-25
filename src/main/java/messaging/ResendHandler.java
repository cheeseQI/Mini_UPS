package messaging;

import common.SeqGenerator;
import model.Truck;
import protocol.AmazonUps;
import protocol.WorldUps;
import service.TruckService;

import java.util.List;

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
            //queryTruckToWorld(); todo: add back when finishing all parts. this print too much
        }
    }

    private void resendToAmazon() {
        AmazonUps.UACommands.Builder uaCommandBuilder = AmazonUps.UACommands.newBuilder();
        if (Server.uaTruckArrivedMap.isEmpty() && Server.uaTruckDeliverMadeMap.isEmpty() && Server.uaUpdatePackageStatusMap.isEmpty()) {// todo: other
            return;
        }
        for (AmazonUps.UATruckArrived uaTruckArrived: Server.uaTruckArrivedMap.values()) {
            uaCommandBuilder.addTruckArrived(uaTruckArrived);
        }
        for (AmazonUps.UATruckDeliverMade uaTruckDeliverMade: Server.uaTruckDeliverMadeMap.values()) {
            uaCommandBuilder.addDelivered(uaTruckDeliverMade);
        }
        for (AmazonUps.UAUpdatePackageStatus uaUpdatePackageStatus: Server.uaUpdatePackageStatusMap.values()) {
            uaCommandBuilder.addUpdatePackageStatus(uaUpdatePackageStatus);
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
        // todo: add other message
    }

    public void queryTruckToWorld() {
        WorldUps.UCommands.Builder uCommandsBuilder = WorldUps.UCommands.newBuilder();
        TruckService truckService = new TruckService();
        List<Truck> truckList = truckService.findAllValidTrucks();
        for (Truck truck: truckList) {
            WorldUps.UQuery.Builder uQuery = WorldUps.UQuery.newBuilder();
            uQuery.setTruckid(truck.getTruckId()).setSeqnum(SeqGenerator.incrementAndGet());
            uCommandsBuilder.addQueries(uQuery);
        }
        System.out.println("send uquery to world");
        Server.worldClient.sendMessage(uCommandsBuilder.build());
    }
}
