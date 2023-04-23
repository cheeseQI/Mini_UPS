package messaging;

import protocol.AmazonUps;
import protocol.WorldUps;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public static WorldClient worldClient;
    public static AmazonClient amazonClient;
    public static Map<Long, WorldUps.UGoPickup> uGoPickupMap;
    public static Map<Long, WorldUps.UGoDeliver> uGoDeliverMap;
    public static Map<Long, AmazonUps.UATruckArrived> uaTruckArrivedMap;
    public static Map<Long, AmazonUps.UATruckDeliverMade> uaTruckDeliverMadeMap;

    public Server() {
        uGoPickupMap = new ConcurrentHashMap<>();
        uGoDeliverMap = new ConcurrentHashMap<>();
        uaTruckArrivedMap = new ConcurrentHashMap<>();
        uaTruckDeliverMadeMap = new ConcurrentHashMap<>();
    }

    public void start() {
        System.out.println("UPS Server is socket with WorldSim");
        worldClient = new WorldClient("127.0.0.1", 12345);
        ReceiveWorldHandler receiveWorldHandler = new ReceiveWorldHandler();
        Thread worldThread = new Thread(receiveWorldHandler);
        worldThread.start();

        try (ServerSocket serverSocket = new ServerSocket(7474)) {
            Socket initSocket = serverSocket.accept();
            amazonClient = new AmazonClient(initSocket);
            System.out.println("UPS Server is socket with Amazon");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReceiveAmazonHandler receiveAmazonHandler = new ReceiveAmazonHandler();
        Thread amazonThread = new Thread(receiveAmazonHandler);
        amazonThread.start();
        //todo: need a server UWhandler && server UU handler
        ResendHandler resendHandler = new ResendHandler();
        Thread resendThread = new Thread(resendHandler);
        resendThread.start();

        //user handler-> query & redirect package

        UserHandler userHandler = new UserHandler();
        Thread userThread = new Thread(userHandler);
        userThread.start();
    }
}
