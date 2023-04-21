package messaging;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import controller.TruckController;
import protocol.AmazonUps;
import protocol.WorldUps;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class AmazonClient extends SocketClient implements Runnable {

    public AmazonClient(Socket socket) throws IOException {
        super(socket);
    }

    public AmazonClient(String host, int port) {
        super(host, port);
    }

    public AmazonUps.AUConnected receiveAUConnected() {
        try {
            return AmazonUps.AUConnected.parseFrom(codedInputStream.readByteArray());
        } catch (IOException e) {
            System.err.println("Error receiving message from " + host + ":" + port);
            e.printStackTrace();
            return null;
        }
    }

    public AmazonUps.AUCommands receiveARequest() {
        try {
            return AmazonUps.AUCommands.parseFrom(codedInputStream.readByteArray());
        } catch (IOException e) {
            System.err.println("Error receiving message from " + host + ":" + port);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void run() {
        // recv AUcommand
        TruckController truckController = new TruckController(host, port);
        AmazonUps.AUCommands auCommands = receiveARequest();
        if (auCommands.getCallTruckCount() > 0) {
            for (AmazonUps.AUCallTruck auCallTruck: auCommands.getCallTruckList()) {
                truckController.pickUp(auCallTruck.getWhid());
            }
        }
        if (auCommands.getTruckGoDeliverCount() > 0) {
            for (AmazonUps.AUTruckGoDeliver auTruckGoDeliver: auCommands.getTruckGoDeliverList()) {
                for (AmazonUps.AUDeliveryLocation auDeliveryLocation: auTruckGoDeliver.getPackagesList()) {
                    truckController.goDeliver(auTruckGoDeliver.getTruckid(), auDeliveryLocation.getX(), auDeliveryLocation.getY(), auDeliveryLocation.getShipid());
                }
            }
        }
        if (auCommands.getRequestPackageStatusCount() > 0) {
            //todo: according to database provide uacommand back
        }
        truckController.close();
    }

    //public WorldAUps.AUxx receiveAURequest();
    //public WorldAUps.AUxx receiveAUResponse();
}
