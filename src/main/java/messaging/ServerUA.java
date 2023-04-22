package messaging;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import common.BuilderUtil;
import common.ConstantUtil;
import protocol.AmazonUps;
import protocol.WorldUps;
import service.TruckService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerUA implements Runnable {
    public static Map<Long, AmazonUps.UACommands> uaCommandsMap;
    public static Map<Long, WorldUps.UCommands> uwCommandsMap;
    private static final int PORT = 7474;
    private WorldClient worldClient;

    public ServerUA() {
        uaCommandsMap = new HashMap<>();
        uwCommandsMap = new HashMap<>();
        worldClient = new WorldClient("127.0.0.1", 12345);
    }

    public void initWorld() {
        TruckService truckService = new TruckService();
        List<WorldUps.UInitTruck> uInitTruckList = truckService.make100Trucks();
        WorldUps.UConnect uConnect = BuilderUtil.buildUConnect(uInitTruckList);
        worldClient.sendMessage(uConnect);
        WorldUps.UConnected uConnected = worldClient.receiveUConnected();
        System.out.println(uConnected.toString());
        if (!uConnected.getResult().equals(ConstantUtil.CONNECT_SUCCESS)) {
            System.out.println(uConnected.getResult());
            return;
        }
        ConstantUtil.WORLD_ID = uConnected.getWorldid();
        //todo: truckService.storeTrucks(uInitTruckList);
    }

    @Override
    public void run() {
        initWorld();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("UPS Server is listening Amazon connect on port " + PORT);
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            Socket initSocket = serverSocket.accept();
            AmazonClient amazonConnectClient = new AmazonClient(initSocket);
            AmazonUps.UAConnect uaConnect = BuilderUtil.buildUAConnect(ConstantUtil.WORLD_ID);
            amazonConnectClient.sendMessage(uaConnect);
            System.out.println(amazonConnectClient.receiveAUConnected()); //todo: may failed or no ack

            while (true) {
                Socket socket = serverSocket.accept();
                AmazonClient amazonCommunicationClient = new AmazonClient(socket);
                executorService.submit(amazonCommunicationClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
