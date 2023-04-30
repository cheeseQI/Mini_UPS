package messaging;

import common.SeqGenerator;
import model.Truck;
import protocol.WorldUps;
import service.TruckService;

import java.util.List;

public class TruckUpdateHandler implements Runnable{
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            queryTruckToWorld();
        }
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
