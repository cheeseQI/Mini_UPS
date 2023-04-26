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
