package messaging;

import com.google.protobuf.Message;
import common.BuilderUtil;
import common.ConstantUtil;
import common.MyBatisUtil;
import common.SeqGenerator;
import mapper.PackageMapper;
import mapper.TruckMapper;
import model.Package;
import model.Truck;
import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.annotation.Transactional;
import protocol.AmazonUps;
import protocol.WorldUps;
import service.PackageService;
import service.TruckService;

import java.util.ArrayList;
import java.util.List;

public class ReceiveWorldHandler implements Runnable{

    @Override
    public void run() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        TruckMapper truckMapper = sqlSession.getMapper(TruckMapper.class);
        TruckService truckService = new TruckService();
        PackageService packageService = new PackageService();
        List<WorldUps.UInitTruck> uInitTruckList = truckService.make30Trucks();
        WorldUps.UConnect uConnect = BuilderUtil.buildUConnect(uInitTruckList);
        Server.worldClient.sendMessage(uConnect);
        WorldUps.UConnected uConnected = Server.worldClient.receiveUConnected();
        System.out.println(uConnected.toString());
        if (!uConnected.getResult().equals(ConstantUtil.CONNECT_SUCCESS)) {
            throw new IllegalArgumentException(uConnected.getResult());
        }
        ConstantUtil.WORLD_ID = uConnected.getWorldid();
        truckService.storeTrucks(uInitTruckList);
        truckService.storeDummyTruck();
        while (true) {
            boolean hasCommandContent = false;
            List<Long> ackList = new ArrayList<>();
            WorldUps.UResponses uResponses = Server.worldClient.receiveUResponse();
            System.out.println("receive uresponse from world: " + uResponses);
            // deal with ack of uResponse, which is used to delete resend uCommands
            for (long ack: uResponses.getAcksList()) {
                if (Server.uGoPickupMap.containsKey(ack)) {
                    //WorldUps.UGoPickup uGoPickup = Server.uGoPickupMap.get(ack);
                    Server.uGoPickupMap.remove(ack);
                    System.out.println("remove uGoPickUp with ack/seq: " + ack);
                } else if (Server.uGoDeliverMap.containsKey(ack)) {
                    //WorldUps.UGoDeliver uGoDeliver = Server.uGoDeliverMap.get(ack);
                    Server.uGoDeliverMap.remove(ack);
                    System.out.println("remove uGoDeliver with ack/seq: " + ack);
                }
            }
            for (WorldUps.UDeliveryMade uDeliveryMade: uResponses.getDeliveredList()) {
                hasCommandContent = true;
                ackList.add(uDeliveryMade.getSeqnum());
                System.out.println("already delivered and put into map: " + uDeliveryMade.getPackageid());
                AmazonUps.UATruckDeliverMade uaTruckDeliverMade = BuilderUtil.buildUATruckDeliverMade(uDeliveryMade.getTruckid(), uDeliveryMade.getPackageid(), SeqGenerator.incrementAndGet());
                Server.uaTruckDeliverMadeMap.put(uaTruckDeliverMade.getSeqnum(), uaTruckDeliverMade);
                Truck truck = truckMapper.findByTruckId(uDeliveryMade.getTruckid());
                truck.setStatus(ConstantUtil.TRUCK_IDLE);
                truck.setPackageNum(truck.getPackageNum() - 1);
                truckService.updateTruck(truck);
                packageService.completePackage(uDeliveryMade.getPackageid());
            }
            for (WorldUps.UFinished uFinished: uResponses.getCompletionsList()) {
                hasCommandContent = true;
                ackList.add(uFinished.getSeqnum());
                Truck truck = truckMapper.findByTruckId(uFinished.getTruckid());
                System.out.println("receive arrived info:" + uFinished);
                System.out.println(truck.getStatus());
                if (truck.getStatus().equals(ConstantUtil.TRUCK_IDLE)) {
                    System.out.println("this means end of a whole shipping");
                    continue;
                }
                List<Package> packages = packageService.findPackageByTruck(truck.getTruckId());
                AmazonUps.UATruckArrived uaTruckArrived = BuilderUtil.buildUATruckArrived(packages, uFinished.getTruckid(), uFinished.getX(), uFinished.getY(), SeqGenerator.incrementAndGet());
                Server.uaTruckArrivedMap.put(uaTruckArrived.getSeqnum(), uaTruckArrived);
                truck.setStatus(ConstantUtil.TRUCK_ARRIVE);
                truck.setPackageNum(truck.getPackageNum() + 1);
                truckMapper.updateTruck(truck);
                sqlSession.commit();
            }
            if (uResponses.getTruckstatusCount() > 0) {
                hasCommandContent = true;
                updateTruckInfo(uResponses, ackList);
            }
            WorldUps.UCommands.Builder  uCommandsBuilder = WorldUps.UCommands.newBuilder();
            // tell world the uresponse that has been received
            uCommandsBuilder.addAllAcks(ackList);
            if (!hasCommandContent) {
                continue;
            }
            //System.out.println("send to world: " + uCommandsBuilder);
            Server.worldClient.sendMessage(uCommandsBuilder.build());
        }
    }

    void updateTruckInfo(WorldUps.UResponses uResponses, List<Long> ackList) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        TruckMapper truckMapper = sqlSession.getMapper(TruckMapper.class);
        TruckService truckService = new TruckService();
        for (WorldUps.UTruck uTruck: uResponses.getTruckstatusList()) {
            ackList.add(uTruck.getSeqnum());
            Truck truck = truckMapper.findByTruckId(uTruck.getTruckid());
            truck.setCurrX(uTruck.getX());
            truck.setCurrY(uTruck.getY());
            truckService.updateTruck(truck);
        }
    }
}
