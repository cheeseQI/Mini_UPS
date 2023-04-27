package service;

import common.MyBatisUtil;
import mapper.PackageMapper;
import mapper.TruckMapper;
import common.BuilderUtil;
import model.Truck;
import org.apache.ibatis.session.SqlSession;
import protocol.WorldUps;
import protocol.UpsUser;
import mapper.*;
import model.Package;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    public void storeUsers(List<User> userList) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            for (User user: userList) {
                userMapper.insertUser(user);
                sqlSession.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public void storeUser(User user) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.insertUser(user);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void storeUser(Integer userId, String password, String userName) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.insertUser(new User(userId,password, userName));
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Package queryPackageById(Long packageId){
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            PackageMapper packageMapper = sqlSession.getMapper(PackageMapper.class);
            return packageMapper.findByPackageId(packageId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Package> queryPackageByUserId(Integer userId){
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            PackageMapper packageMapper = sqlSession.getMapper(PackageMapper.class);
            return packageMapper.findByUserId(userId);
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
                return sqlAck == 1 ? "" : "update failed" ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
