package common;

import com.google.protobuf.Message;
import protocol.AmazonUps;
import protocol.WorldUps;
import protocol.UpsUser;
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

    public static WorldUps.UGoDeliver buildUGoDeliver(int truckId, List<WorldUps.UDeliveryLocation> uDeliveryLocationList, long seqNum) {
        WorldUps.UGoDeliver.Builder builder = WorldUps.UGoDeliver.newBuilder();
        builder.setTruckid(truckId);
        builder.addAllPackages(uDeliveryLocationList);
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
        builder.setTruckid(truckId).setX(x).setY(y).setSeqnum(seqNum);
        return builder.build();
    }

    public static AmazonUps.UATruckDeliverMade buildUATruckDeliverMade(int truckId, long packageId, long seqNum) {
        AmazonUps.UATruckDeliverMade.Builder builder = AmazonUps.UATruckDeliverMade.newBuilder();
        builder.setTruckid(truckId).setPackageid(packageId).setSeqnum(seqNum);
        return builder.build();
    }

    public static UpsUser.UPackage buildUPackage(long id, String status,String description, int itemCount,int x,int y) {
        UpsUser.UPackage.Builder builder = UpsUser.UPackage.newBuilder();
        builder.setId(id).setStatus(status).setDescription(description).setItemCount(itemCount).setX(x).setY(y);
        return builder.build();
    }

    public static UpsUser.UQuery buildUQuery(long packageId, int userId) {
        UpsUser.UQuery.Builder builder = UpsUser.UQuery.newBuilder();
        builder.setPackageId(packageId).setUserId(userId);
        return builder.build();
    }

    public static UpsUser.UQueryResult buildUQueryResult(List<UpsUser.UPackage> packageList, int ack) {
        UpsUser.UQueryResult.Builder builder = UpsUser.UQueryResult.newBuilder();
        builder.addAllPackage(packageList).setAck(ack);
        return builder.build();
    }
    public static UpsUser.URedirect buildURedirect(long packageId,int x,int y) {
        UpsUser.URedirect.Builder builder = UpsUser.URedirect.newBuilder();
        builder.setPackageId(packageId).setX(x).setY(y);
        return builder.build();
    }

    public static UpsUser.URedirectResult buildURedirectResult(String message) {
        UpsUser.URedirectResult.Builder builder = UpsUser.URedirectResult.newBuilder();
        builder.setMessage(message);
        return builder.build();
    }

    public static UpsUser.UUserRequest buildUUserRequest(UpsUser.UQuery queryCommand,UpsUser.URedirect redirectCommand) {
        UpsUser.UUserRequest.Builder builder = UpsUser.UUserRequest.newBuilder();
        builder.setQueryCommand(queryCommand).setRedirectCommand(redirectCommand);
        return builder.build();
    }
    public static UpsUser.UUserResponse buildUUserResponse(UpsUser.UQueryResult queryResult,UpsUser.URedirectResult redirectResult) {
        UpsUser.UUserResponse.Builder builder = UpsUser.UUserResponse.newBuilder();
        builder.setQueryResult(queryResult).setRedirectResult(redirectResult);
        return builder.build();
    }
    public static UpsUser.UUserResponse buildUUserResponse(UpsUser.UQueryResult queryResult) {
        UpsUser.UUserResponse.Builder builder = UpsUser.UUserResponse.newBuilder();
        builder.setQueryResult(queryResult);
        return builder.build();
    }

    public static UpsUser.UUserResponse buildUUserResponse(UpsUser.URedirectResult redirectResult) {
        UpsUser.UUserResponse.Builder builder = UpsUser.UUserResponse.newBuilder();
        builder.setRedirectResult(redirectResult);
        return builder.build();
    }
}
