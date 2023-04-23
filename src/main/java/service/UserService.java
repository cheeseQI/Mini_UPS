package service;

import common.MyBatisUtil;
import mapper.PackageMapper;
import mapper.TruckMapper;
import common.BuilderUtil;
import mapper.UserMapper;
import model.*;
import model.Package;
import org.apache.ibatis.session.SqlSession;
import protocol.WorldUps;

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
    
    public void storeUser(Integer userId, String password) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.insertUser(new User(userId,password));
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Package queryPackageById(Integer packageId){
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
    public String redirectPackage(Integer packageId, int destX, int destY){
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            PackageMapper packageMapper = sqlSession.getMapper(PackageMapper.class);
            Package pkg = packageMapper.findByPackageId(packageId);
            if(pkg.getStatus()!= "IDLE"){
                return "package is not idle";
            } else {
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
