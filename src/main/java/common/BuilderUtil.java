package common;

import com.google.protobuf.Message;
import protocol.AmazonUps;
import protocol.WorldUps;

import java.util.List;

public class BuilderUtil {

    public static WorldUps.UInitTruck buildUInitTruck(int x, int y, int id) {
        WorldUps.UInitTruck.Builder uInitTruckBuilder = WorldUps.UInitTruck.newBuilder();
        uInitTruckBuilder.setX(x).setY(y).setId(id);
        uInitTruckBuilder.build();
        return uInitTruckBuilder.build();
    }


    public static WorldUps.UConnect buildUConnect(long id, List<WorldUps.UInitTruck> uInitTruckList) {
        WorldUps.UConnect.Builder builder = WorldUps.UConnect.newBuilder();
        builder.setWorldid(id);
        builder.setIsAmazon(false);
        builder.addAllTrucks(uInitTruckList);
        return builder.build();
    }

    public static AmazonUps.UAConnect buildUAConnect(long id) {
        AmazonUps.UAConnect.Builder builder = AmazonUps.UAConnect.newBuilder();
        builder.setWorldid(id);
        return builder.build();
    }


    public static WorldUps.UConnect buildUConnect(List<WorldUps.UInitTruck> uInitTruckList) {
        WorldUps.UConnect.Builder builder = WorldUps.UConnect.newBuilder();
        builder.setIsAmazon(false);
        builder.addAllTrucks(uInitTruckList);
        return builder.build();
    }

    public static WorldUps.UGoPickup buildUGoPickup(int truckId, int whId, long seqNum) {
        WorldUps.UGoPickup.Builder builder = WorldUps.UGoPickup.newBuilder();
        builder.setTruckid(truckId);
        builder.setWhid(whId);
        builder.setSeqnum(seqNum);
        return builder.build();
    }

    public static WorldUps.UDeliveryLocation buildUDeliveryLocation(long packageId, int x, int y) {
        WorldUps.UDeliveryLocation.Builder builder = WorldUps.UDeliveryLocation.newBuilder();
        builder.setPackageid(packageId);
        builder.setX(x);
        builder.setY(y);
        return builder.build();
    }

    public static WorldUps.UGoDeliver buildUGoDeliver(int truckId, WorldUps.UDeliveryLocation uDeliveryLocation, long seqNum) {
        WorldUps.UGoDeliver.Builder builder = WorldUps.UGoDeliver.newBuilder();
        builder.setTruckid(truckId);
        builder.addPackages(uDeliveryLocation);
        builder.setSeqnum(seqNum);
        return builder.build();
    }

    public static WorldUps.UQuery buildUQuery(int truckId, long seqNum) {
        WorldUps.UQuery.Builder builder = WorldUps.UQuery.newBuilder();
        builder.setTruckid(truckId).setSeqnum(seqNum);
        return builder.build();
    }

    //todo: need to be modified
    public static AmazonUps.UATruckArrived buildUATruckArrived(int truckId, int x, int y, long seqNum) {
        AmazonUps.UATruckArrived.Builder builder = AmazonUps.UATruckArrived.newBuilder();
        builder.setTruckid(truckId);
        return builder.build();
    }

    public static AmazonUps.UATruckDeliverMade buildUATruckDeliverMade(int truckId, long packageId, long seqNum) {
        AmazonUps.UATruckDeliverMade.Builder builder = AmazonUps.UATruckDeliverMade.newBuilder();
        builder.setTruckid(truckId).setPackageid(packageId).setSeqnum(seqNum);
        return builder.build();
    }
}
