package model;

import common.MyBatisUtil;
import junit.framework.TestCase;
import mapper.PackageMapper;
import mapper.TruckMapper;
import org.apache.ibatis.session.SqlSession;

public class TruckTest extends TestCase {
    private SqlSession sqlSession;
    private TruckMapper truckMapper;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        sqlSession = MyBatisUtil.getSqlSession();
        truckMapper = sqlSession.getMapper(TruckMapper.class);
        truckMapper.deleteAll();
        int res = truckMapper.insertTruck(new Truck(1, "IDLE", 1, 2, 1));
        System.out.println(res);
        sqlSession.commit();
    }

    public void testGetTruckId() {
        System.out.println(truckMapper.findByTruckId(1));
    }

    public void testSetTruckStatus() {
        Truck truck = truckMapper.findByTruckId(1);
        truck.setStatus("EN ROUTE");
        truckMapper.updateTruck(truck);
        sqlSession.commit();
        testGetTruckId();
    }
}