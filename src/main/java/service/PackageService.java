package service;

import common.MyBatisUtil;
import mapper.PackageMapper;
import mapper.TruckMapper;
import model.Package;
import model.Truck;
import org.apache.ibatis.session.SqlSession;
import protocol.WorldUps;

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
}
