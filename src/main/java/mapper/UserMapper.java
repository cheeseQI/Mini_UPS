package mapper;

import model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user_table")
    List<User> findAll();

    @Select("SELECT * FROM user_table")
    boolean authentication(String username,String password);

    @Select("SELECT * FROM user_table WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    @Insert("INSERT INTO user_table (username, password) VALUES (#{username}, #{password})")
    int insertUser(User user);

    @Update("UPDATE user_table SET password = #{password} WHERE username = #{username}")
    int updateUser(User user);

    @Delete("DELETE FROM user_table WHERE username = #{username}")
    int deleteUser(@Param("username") String username);

    @Delete("DELETE FROM user_table")
    int deleteAll();
}
