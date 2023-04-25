package mapper;

import model.Truck;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TruckMapper {

    @Select("SELECT * FROM truck")
    List<Truck> findAll();

    @Select("SELECT * FROM truck where status = 'IDLE'")
    List<Truck> findAllIdle();

    @Select("SELECT * FROM truck where truckId != 0")
    List<Truck> findAllValid();

    @Select("SELECT * FROM truck WHERE truckId = #{truckId}")
    Truck findByTruckId(@Param("truckId") Integer truckId);

    @Insert("INSERT INTO truck (truckId, status, currX, currY, packageNum) VALUES (#{truckId}, #{status}, #{currX}, #{currY}, #{packageNum})")
    int insertTruck(Truck truck);

    @Update("UPDATE truck SET status = #{status}, currX = #{currX}, currY = #{currY}, packageNum = #{packageNum} WHERE truckId = #{truckId}")
    int updateTruck(Truck truck);

    @Delete("DELETE FROM truck WHERE truckId = #{truckId}")
    int deleteTruck(@Param("truckId") Integer truckId);

    @Delete("DELETE FROM truck")
    int deleteAll();
}
