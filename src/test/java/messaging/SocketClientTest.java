package messaging;

import junit.framework.TestCase;
import protocol.WorldUps;

import java.util.ArrayList;
import java.util.List;

public class SocketClientTest extends TestCase {
    String host = "127.0.0.1";
    int port = 12345;

    public void testConnect() {
        SocketClient socketClient = new SocketClient(host, port);
        socketClient.connect();
        System.out.println("successful connect");
        socketClient.disconnect();
    }

    public void testSendMessage() {
        SocketClient socketClient = new SocketClient(host, port);
        socketClient.connect();
        //WorldUps.UInitTruck uInitTruck = BuilderUtil.buildUInitTruck(1, 1, 3);
        List<WorldUps.UInitTruck> uInitTruckList = new ArrayList<>();
        //uInitTruckList.add(uInitTruck);
        WorldUps.UConnect uConnect = BuilderUtil.buildUConnect(1, uInitTruckList);
        socketClient.sendMessage(uConnect);
        WorldUps.UConnected uConnected =  socketClient.receiveUConnected();
        System.out.println(uConnected.toString());
        socketClient.disconnect();
    }
}
