package messaging;

import common.BuilderUtil;
import service.TruckService;
import junit.framework.TestCase;
import protocol.WorldUps;

import java.util.List;

public class WorldClientTest extends TestCase {
    String host = "127.0.0.1";
    int port = 12345;

    public void testConnect() {
        WorldClient worldClient = new WorldClient(host, port);
        System.out.println("successful connect");
        worldClient.disconnect();
    }

    public void testSendMessage() {
        TruckService truckService = new TruckService();
        WorldClient worldClient = new WorldClient(host, port);
        //WorldUps.UInitTruck uInitTruck = BuilderUtil.buildUInitTruck(1, 1, 3);
        List<WorldUps.UInitTruck> uInitTruckList = truckService.make100Trucks();
        //uInitTruckList.add(uInitTruck);
        WorldUps.UConnect uConnect = BuilderUtil.buildUConnect(1, uInitTruckList);
        worldClient.sendMessage(uConnect);
        WorldUps.UConnected uConnected =  worldClient.receiveUConnected();
        System.out.println(uConnected.toString());
        worldClient.disconnect();
    }
}
