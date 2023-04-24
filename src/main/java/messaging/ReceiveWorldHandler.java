package messaging;

import com.google.protobuf.Message;
import common.BuilderUtil;
import common.ConstantUtil;
import common.MyBatisUtil;
import common.SeqGenerator;
import mapper.TruckMapper;
import model.Truck;
import org.apache.ibatis.session.SqlSession;
import protocol.AmazonUps;
import protocol.WorldUps;
import service.TruckService;

import java.util.ArrayList;
import java.util.List;

public class ReceiveWorldHandler implements Runnable{

    @Override
    public void run() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        TruckMapper truckMapper = sqlSession.getMapper(TruckMapper.class);
        TruckService truckService = new TruckService();
        List<WorldUps.UInitTruck> uInitTruckList = truckService.make100Trucks();
        WorldUps.UConnect uConnect = BuilderUtil.buildUConnect(uInitTruckList);
        Server.worldClient.sendMessage(uConnect);
        WorldUps.UConnected uConnected = Server.worldClient.receiveUConnected();
        System.out.println(uConnected.toString());
        if (!uConnected.getResult().equals(ConstantUtil.CONNECT_SUCCESS)) {
            throw new IllegalArgumentException(uConnected.getResult());
        }
        ConstantUtil.WORLD_ID = uConnected.getWorldid();
        truckService.storeTrucks(uInitTruckList);
        while (true) {
            boolean hasCommandContent = false;
            List<Long> ackList = new ArrayList<>();
            WorldUps.UResponses uResponses = Server.worldClient.receiveUResponse();
            System.out.println("receive uresponse from world: " + uResponses);
            // deal with ack of uResponse, which is used to delete resend uCommands
            for (long ack: uResponses.getAcksList()) {
                if (Server.uGoPickupMap.containsKey(ack)) {
                    WorldUps.UGoPickup uGoPickup = Server.uGoPickupMap.get(ack);
                    Server.uGoPickupMap.remove(ack);
                    System.out.println("remove uGoPickUp with ack/seq: " + ack);
                } else if (Server.uGoDeliverMap.containsKey(ack)) {
                    WorldUps.UGoDeliver uGoDeliver = Server.uGoDeliverMap.get(ack);
                    Server.uGoDeliverMap.remove(ack);
                    System.out.println("remove uGoDeliver with ack/seq: " + ack);
                }
            }
            for (WorldUps.UFinished uFinished: uResponses.getCompletionsList()) {
                hasCommandContent = true;
                //todo: change builder
                ackList.add(uFinished.getSeqnum());
                AmazonUps.UATruckArrived uaTruckArrived = BuilderUtil.buildUATruckArrived(uFinished.getTruckid(), uFinished.getX(), uFinished.getY(), SeqGenerator.incrementAndGet());
                Server.uaTruckArrivedMap.put(uaTruckArrived.getSeqnum(), uaTruckArrived);
                truckService.setTruckStatus(uFinished.getTruckid(), ConstantUtil.TRUCK_ARRIVE);
            }
            for (WorldUps.UDeliveryMade uDeliveryMade: uResponses.getDeliveredList()) {
                hasCommandContent = true;
                ackList.add(uDeliveryMade.getSeqnum());
                AmazonUps.UATruckDeliverMade uaTruckDeliverMade = BuilderUtil.buildUATruckDeliverMade(uDeliveryMade.getTruckid(), uDeliveryMade.getPackageid(), SeqGenerator.incrementAndGet());
                Server.uaTruckDeliverMadeMap.put(uaTruckDeliverMade.getSeqnum(), uaTruckDeliverMade);
                truckService.setTruckStatus(uDeliveryMade.getTruckid(), ConstantUtil.TRUCK_IDLE);
            }
            WorldUps.UCommands.Builder  uCommandsBuilder = WorldUps.UCommands.newBuilder();
            // tell world the uresponse that has been received
            uCommandsBuilder.addAllAcks(ackList);
            if (!hasCommandContent) {
                continue;
            }
            System.out.println("send to world: " + uCommandsBuilder);
            Server.worldClient.sendMessage(uCommandsBuilder.build());
        }
    }
}
