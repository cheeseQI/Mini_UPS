package mapper;

import model.Package;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface PackageMapper {

    @Select("SELECT * FROM package")
    List<Package> findAll();

    @Select("SELECT * FROM package WHERE packageId = #{packageId}")
    Package findByPackageId(@Param("packageId") Integer packageId);

    @Insert("INSERT INTO package (packageId, status, description, itemNum, truckId, userId, destX, destY, whid) VALUES (#{packageId}, #{status}, #{description}, #{itemNum}, #{truckId}, #{userId}, #{destX}, #{destY}, #{whid})")
    int insertPackage(Package pack);

    @Update("UPDATE package SET status = #{status}, description = #{description}, itemNum = #{itemNum}, truckId = #{truckId}, userId = #{userId}, destX = #{destX}, destY = #{destY}, whid = #{whid} WHERE packageId = #{packageId}")
    int updatePackage(Package pack);

    @Delete("DELETE FROM package WHERE packageId = #{packageId}")
    int deletePackage(@Param("packageId") Integer packageId);

    @Delete("DELETE FROM package")
    int deleteAll();
}

