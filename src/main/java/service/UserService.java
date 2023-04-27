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
    public boolean authentication(String username, String password){
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            User user = new User(username,password);
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
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
    
    public void storeUser(String username, String password) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.insertUser(new User(username,password));
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

}
