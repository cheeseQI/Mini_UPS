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
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.findByUsername(username);
            if(user != null && password.equals(user.getPassword())){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

}
