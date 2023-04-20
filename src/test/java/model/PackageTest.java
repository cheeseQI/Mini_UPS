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
        packageMapper.deleteAll();
        packageMapper.insertPackage(new Package(2, "IDLE", "apple from silicon", 1, 1, 747, 10, 10, 1));
        sqlSession.commit();
    }

    public void testGetPackageId() {
        System.out.println(packageMapper.findByPackageId(2));
    }

    public void testSetPackage() {
        Package pkg = packageMapper.findByPackageId(2);
        pkg.setDescription("apple from cool house");
        packageMapper.updatePackage(pkg);
        sqlSession.commit();
    }
}