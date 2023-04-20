package mapper;

import model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user_table")
    List<User> findAll();

    @Select("SELECT * FROM user_table WHERE userId = #{userId}")
    User findByUserId(@Param("userId") Integer userId);

    @Insert("INSERT INTO user_table (userId, password) VALUES (#{userId}, #{password})")
    int insertUser(User user);

    @Update("UPDATE user_table SET password = #{password} WHERE userId = #{userId}")
    int updateUser(User user);

    @Delete("DELETE FROM user_table WHERE userId = #{userId}")
    int deleteUser(@Param("userId") String userId);

    @Delete("DELETE FROM user_table")
    int deleteAll();
}
