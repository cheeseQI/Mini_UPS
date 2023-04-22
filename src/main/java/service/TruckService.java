package service;

import common.MyBatisUtil;
import mapper.TruckMapper;
import common.BuilderUtil;
import model.Truck;
import org.apache.ibatis.session.SqlSession;
import protocol.WorldUps;

import java.util.ArrayList;
import java.util.List;

public class TruckService {

    public List<WorldUps.UInitTruck> make100Trucks() {
        List<WorldUps.UInitTruck> uInitTruckList = new ArrayList<>();
        for (int i = 0; i < 100; i ++) {
            WorldUps.UInitTruck uInitTruck = BuilderUtil.buildUInitTruck(i, i, i);
            uInitTruckList.add(uInitTruck);
        }
        return uInitTruckList;
    }

    //todo: atomic
    public void storeTrucks(List<WorldUps.UInitTruck> uInitTruckList) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            TruckMapper truckMapper = sqlSession.getMapper(TruckMapper.class);
            for (WorldUps.UInitTruck uInitTruck: uInitTruckList) {
                truckMapper.insertTruck(new Truck(uInitTruck.getId(), "IDLE", uInitTruck.getX(), uInitTruck.getY(), 0));
                sqlSession.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //todo: add short-distance algorithm, and once recv ack, rememeber to update database
    public Truck findTruckToPickUp() {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            TruckMapper truckMapper = sqlSession.getMapper(TruckMapper.class);
            List<Truck> truckList = truckMapper.findAllIdle();
            return truckList.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
