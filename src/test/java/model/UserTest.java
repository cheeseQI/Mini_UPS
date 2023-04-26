package model;

import junit.framework.TestCase;
import mapper.PackageMapper;
import mapper.TruckMapper;
import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import common.MyBatisUtil;

public class UserTest extends TestCase {
    private SqlSession sqlSession;
    private UserMapper userMapper;
    private TruckMapper truckMapper;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        sqlSession = MyBatisUtil.getSqlSession();
        userMapper = sqlSession.getMapper(UserMapper.class);
        truckMapper = sqlSession.getMapper(TruckMapper.class);
        //userMapper.deleteAll();
        //truckMapper.deleteAll();
        //userMapper.insertUser(new User(747, "12345"));
        //sqlSession.commit();
    }

    public void testGetUserIdAndPassword() {
        System.out.println(userMapper.findByUserId(747));
    }

    public void testSetPassword() {
//        User user = userMapper.findByUserId(747);
//        System.out.println(user);
//        user.setPassword("321");
//        userMapper.updateUser(user);
//        sqlSession.commit();
//        System.out.println(userMapper.findByUserId(747));
    }

    public void testClean() {
        userMapper.deleteAll();
        sqlSession.commit();
    }

//    @Override
//    protected void tearDown() throws Exception {
//        userMapper.deleteAll();
//        sqlSession.commit();
//        sqlSession.close();
//        super.tearDown();
//    }
}