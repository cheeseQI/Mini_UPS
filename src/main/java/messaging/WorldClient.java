package messaging;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import common.BuilderUtil;
import common.SeqGenerator;
import model.Truck;
import protocol.WorldUps;
import service.TruckService;

import java.io.*;
import java.net.Socket;
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
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<WorldUps.UResponses> future = executor.submit(new Callable<WorldUps.UResponses>() {
            @Override
            public WorldUps.UResponses call() throws Exception {
                return WorldUps.UResponses.parseFrom(codedInputStream.readByteArray());
            }
        });

        try {
            // only wait result for 5 second
            WorldUps.UResponses uResponses = future.get(5, TimeUnit.SECONDS);
            System.out.println(uResponses);
            for (long ack: uResponses.getAcksList()) {
                if (ServerUA.uwCommandsMap.containsKey(ack)) {
                    ServerUA.uwCommandsMap.remove(ack);
                }
            }
            return uResponses;
        } catch (TimeoutException e) {
            System.out.println("Timeout: did not receive a message within 3 seconds.");
            future.cancel(true);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        return null;
    }

    public void sendUCommands(WorldUps.UCommands uCommands) {
        sendMessage(uCommands);
        WorldUps.UResponses uResponses = receiveUResponse();
        if (uResponses != null) {
            updateData(uResponses);
        }
    }

    public void sendUGoPickup(int whid) {
        Truck truck = truckService.findTruckToPickUp();
        System.out.println(truck);
        WorldUps.UGoPickup uGoPickup = BuilderUtil.buildUGoPickup(truck.getTruckId(), whid, SeqGenerator.incrementAndGet());
        WorldUps.UCommands.Builder uCommandsBuilder = WorldUps.UCommands.newBuilder();
        uCommandsBuilder.addPickups(uGoPickup);
        ServerUA.uwCommandsMap.put(uGoPickup.getSeqnum(), uCommandsBuilder.build()); // add to record
        sendMessage(uCommandsBuilder.build());
        WorldUps.UResponses uResponses = receiveUResponse();
        // maye be sent ack with next response together
        if (uResponses != null) {
            updateData(uResponses);
        }
    }

    public void sendUGoDeliver(int truckId, int x, int y, long packageId) {
        WorldUps.UGoDeliver uGoDeliver = BuilderUtil.buildUGoDeliver(truckId, BuilderUtil.buildUDeliveryLocation(packageId, x, y), SeqGenerator.incrementAndGet());
        WorldUps.UCommands.Builder uCommandsBuilder = WorldUps.UCommands.newBuilder();
        uCommandsBuilder.addDeliveries(uGoDeliver);
        ServerUA.uwCommandsMap.put(uGoDeliver.getSeqnum(), uCommandsBuilder.build());
        sendMessage(uCommandsBuilder.build());
        WorldUps.UResponses uResponses = receiveUResponse();
        if (uResponses != null) {
            updateData(uResponses);
        }
    }

    public void updateData(WorldUps.UResponses uResponses) {
        if (uResponses.getCompletionsCount() > 0) {
            //todo: change database status
        }
        if (uResponses.getDeliveredCount() > 0) {
            //todo: change database status
        }
    }
}

