package mapper;

import model.Package;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface PackageMapper {

    @Select("SELECT * FROM package")
    List<Package> findAll();

    @Select("SELECT * FROM package WHERE packageId = #{packageId}")
    Package findByPackageId(@Param("packageId") Long packageId);

    @Select("SELECT * FROM package WHERE username = #{username}")
    List<Package> findByUsername(@Param("username") String username);

    @Select("SELECT * FROM package WHERE truckId = #{truckId}")
    List<Package> findByTruckId(@Param("truckId") Integer truckId);

    @Insert("INSERT INTO package (packageId, description, itemNum, truckId, username, destX, destY, whid) VALUES (#{packageId}, #{description}, #{itemNum}, #{truckId}, #{username}, #{destX}, #{destY}, #{whid})")
    int insertPackage(Package pack);

    @Update("UPDATE package SET description = #{description}, itemNum = #{itemNum}, truckId = #{truckId}, username = #{username}, destX = #{destX}, destY = #{destY}, whid = #{whid} WHERE packageId = #{packageId}")
    int updatePackage(Package pack);

    @Delete("DELETE FROM package WHERE packageId = #{packageId}")
    int deletePackage(@Param("packageId") Integer packageId);

    @Delete("DELETE FROM package")
    int deleteAll();
}

