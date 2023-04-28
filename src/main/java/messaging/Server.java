package messaging;

import common.ConstantUtil;
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
    public static Map<Long, AmazonUps.UAUpdatePackageStatus> uaUpdatePackageStatusMap;
    public static Map<Long, AmazonUps.AURequestSendUserInfo> auRequestSendUserInfoMap;


    public Server() {
        uGoPickupMap = new ConcurrentHashMap<>();
        uGoDeliverMap = new ConcurrentHashMap<>();
        uaTruckArrivedMap = new ConcurrentHashMap<>();
        uaTruckDeliverMadeMap = new ConcurrentHashMap<>();
        uaUpdatePackageStatusMap = new ConcurrentHashMap<>();
        auRequestSendUserInfoMap = new ConcurrentHashMap<>();
    }

    public void start() {
        System.out.println("UPS Server is socket with WorldSim");
        worldClient = new WorldClient("vcm-32430.vm.duke.edu", ConstantUtil.WORLD_PORT);
        ReceiveWorldHandler receiveWorldHandler = new ReceiveWorldHandler();
        Thread worldThread = new Thread(receiveWorldHandler);
        worldThread.start();

        try (ServerSocket serverSocket = new ServerSocket(ConstantUtil.UPS_PORT)) {
            Socket initSocket = serverSocket.accept();
            amazonClient = new AmazonClient(initSocket);
            System.out.println("UPS Server is socket with Amazon");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReceiveAmazonHandler receiveAmazonHandler = new ReceiveAmazonHandler();
        Thread amazonThread = new Thread(receiveAmazonHandler);
        amazonThread.start();

        ResendHandler resendHandler = new ResendHandler();
        Thread resendThread = new Thread(resendHandler);
        resendThread.start();

        //user handler-> query & redirect package
        UserHandler userHandler = new UserHandler();
        Thread userThread = new Thread(userHandler);
        userThread.start();

        //update truck handler -> update truck table
        TruckUpdateHandler truckUpdateHandler = new TruckUpdateHandler();
        Thread truckThread = new Thread(truckUpdateHandler);
        truckThread.start();
    }
}
