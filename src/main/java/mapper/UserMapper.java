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

    @Select("SELECT * FROM user_table WHERE username = #{userName}")
    User findByUserName(@Param("userName") String userName);

    @Insert("INSERT INTO user_table (userId, password, username) VALUES (#{userId}, #{password}, #{userName})")
    int insertUser(User user);

    @Update("UPDATE user_table SET password = #{password}, username = #{userName} WHERE userId = #{userId}")
    int updateUser(User user);

    @Delete("DELETE FROM user_table WHERE userId = #{userId}")
    int deleteUser(@Param("userId") Integer userId);

    @Delete("DELETE FROM user_table")
    int deleteAll();
}
