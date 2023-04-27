package service;

import common.MyBatisUtil;
import mapper.PackageMapper;
import mapper.TruckMapper;
import model.Package;
import model.Truck;
import org.apache.ibatis.session.SqlSession;
import protocol.WorldUps;

import java.util.ArrayList;
import java.util.List;

public class PackageService {

    public Package queryPackageById(Long packageId){
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            PackageMapper packageMapper = sqlSession.getMapper(PackageMapper.class);
            return packageMapper.findByPackageId(packageId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Package> queryPackageByUsername(String username){
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            PackageMapper packageMapper = sqlSession.getMapper(PackageMapper.class);
            return packageMapper.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //todo: need atomic, update fail logic
    public String redirectPackage(Long packageId, int destX, int destY){
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            PackageMapper packageMapper = sqlSession.getMapper(PackageMapper.class);
            TruckMapper truckMapper = sqlSession.getMapper(TruckMapper.class);
            Package pkg = packageMapper.findByPackageId(packageId);
            if(truckMapper.findByTruckId(pkg.getTruckId()).getStatus().equals("DELIVERING") || pkg.getTruckId() == 0){
                return "package is already on the way of delivering";
            }
            else{
                pkg.setDestX(destX);
                pkg.setDestY(destY);
                int sqlAck = packageMapper.updatePackage(pkg);
                sqlSession.commit();
                return sqlAck == 1 ? "package update succeeded" : "package update failed" ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Package findPackage(long packageId) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            PackageMapper packageMapper = sqlSession.getMapper(PackageMapper.class);
            return packageMapper.findByPackageId(packageId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Package> findPackageByTruck(int truckId) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            PackageMapper packageMapper = sqlSession.getMapper(PackageMapper.class);
            return packageMapper.findByTruckId(truckId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void insertPackage(long packageId, String description, int itemNum, int truckId, int userId, int destX, int destY, int whid, int startX, int startY) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            PackageMapper packageMapper = sqlSession.getMapper(PackageMapper.class);
            Package pkg = new Package(packageId, description, itemNum, truckId, userId, destX, destY, whid, startX, startY);
            packageMapper.insertPackage(pkg);
            sqlSession.commit();
            System.out.println("insert package" + pkg + " to table!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void completePackage(long packageId) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            PackageMapper packageMapper = sqlSession.getMapper(PackageMapper.class);
            Package pkg = packageMapper.findByPackageId(packageId);
            pkg.setTruckId(0);
            packageMapper.updatePackage(pkg);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
