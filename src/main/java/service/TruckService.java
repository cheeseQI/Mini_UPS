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
    //this is only used to show a non-exist relation for package table
    public void storeDummyTruck() {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            TruckMapper truckMapper = sqlSession.getMapper(TruckMapper.class);
            Truck truck = new Truck(0, "COMPLETE", -1, -1, -1);
            truckMapper.insertTruck(truck);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<WorldUps.UInitTruck> make100Trucks() {
        List<WorldUps.UInitTruck> uInitTruckList = new ArrayList<>();
        for (int i = 1; i <= 100; i ++) {
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
            }
            sqlSession.commit();
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

    public void setTruckStatus(int truckId, String status) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            TruckMapper truckMapper = sqlSession.getMapper(TruckMapper.class);
            Truck truck = truckMapper.findByTruckId(truckId);
            truck.setStatus(status);
            truckMapper.updateTruck(truck);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Truck findTruckById(int truckId) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            TruckMapper truckMapper = sqlSession.getMapper(TruckMapper.class);
            return truckMapper.findByTruckId(truckId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateTruck(Truck truck) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            TruckMapper truckMapper = sqlSession.getMapper(TruckMapper.class);
            truckMapper.updateTruck(truck);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Truck> findAllValidTrucks() {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            TruckMapper truckMapper = sqlSession.getMapper(TruckMapper.class);
            return truckMapper.findAllValid();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
