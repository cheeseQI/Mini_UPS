package service;

import common.MyBatisUtil;
import common.WeightedScoreCalculator;
import mapper.TruckMapper;
import common.BuilderUtil;
import model.Truck;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import protocol.WorldUps;

import java.util.ArrayList;
import java.util.List;
@Service
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

    public List<WorldUps.UInitTruck> make30Trucks() {
        List<WorldUps.UInitTruck> uInitTruckList = new ArrayList<>();
        for (int i = 1; i <= 30; i ++) {
            WorldUps.UInitTruck uInitTruck = BuilderUtil.buildUInitTruck(i, i, i);
            uInitTruckList.add(uInitTruck);
        }
        return uInitTruckList;
    }


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

    // short-distance & min-package weight algorithm to find package
    public Truck findNearestTruckToPickUp(int whX, int whY) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            TruckMapper truckMapper = sqlSession.getMapper(TruckMapper.class);
            List<Truck> truckList = truckMapper.findAllIdle();
            double maxScore = Integer.MIN_VALUE;
            int maxIdx = 0;
            for (int i = 0; i < truckList.size(); i++) {
                Truck truck = truckList.get(i);
                double deltaX = truck.getCurrX() - whX;
                double deltaY = truck.getCurrY() - whY;
                double dis = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                double score = WeightedScoreCalculator.calculateWeightedScore((double)truck.getPackageNum(), dis);
                if (score >= maxScore) {
                    maxScore = score;
                    maxIdx = i;
                }
            }
            return truckList.get(maxIdx);
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
