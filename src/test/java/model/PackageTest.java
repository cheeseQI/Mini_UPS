package model;

import common.MyBatisUtil;
import junit.framework.TestCase;
import mapper.PackageMapper;
import mapper.TruckMapper;
import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Pack;

public class PackageTest extends TestCase {
    private SqlSession sqlSession;
    private PackageMapper packageMapper;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        sqlSession = MyBatisUtil.getSqlSession();
        packageMapper = sqlSession.getMapper(PackageMapper.class);
//        packageMapper.deleteAll();
//        Package pkg = new Package(1,"cheese",4,1,1,2,2,1)
//        packageMapper.insertPackage(new Package(1,"chees));
        //sqlSession.commit();
    }

    public void testGetPackageId() {
//        System.out.println(packageMapper.findByPackageId(2L));
    }

    public void testSetPackage() {
//        Package pkg = packageMapper.findByPackageId(2L);
//        pkg.setDescription("apple from cool house");
//        packageMapper.updatePackage(pkg);
//        sqlSession.commit();
    }

    public void testClean() {
        packageMapper.deleteAll();
        sqlSession.commit();
    }
}