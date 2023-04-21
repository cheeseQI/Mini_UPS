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
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerUA implements Runnable {
    private static final int PORT = 7474;
    private WorldClient worldClient = new WorldClient("127.0.0.1", 12345);
    private CodedInputStream codedInputStream;
    private CodedOutputStream codedOutputStream;

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
            InputStream inputStream = initSocket.getInputStream();
            OutputStream outputStream = initSocket.getOutputStream();
            codedInputStream = CodedInputStream.newInstance(inputStream);
            codedOutputStream = CodedOutputStream.newInstance(outputStream);
            AmazonClient amazonClient = new AmazonClient(initSocket);
            AmazonUps.UAConnect uaConnect = BuilderUtil.buildUAConnect(ConstantUtil.WORLD_ID);
            amazonClient.sendMessage(uaConnect);
            System.out.println(amazonClient.receiveAUConnected()); //todo: may failed or no ack
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
