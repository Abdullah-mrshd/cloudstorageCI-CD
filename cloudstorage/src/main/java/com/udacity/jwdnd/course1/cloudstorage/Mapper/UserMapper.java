package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper
{
    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstname}, #{lastname})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    int insert(User user);




    @Delete("DELETE FROM USERS WHERE username = #{username}")
    void remove(User user);

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    @Select("SELECT userid FROM USERS WHERE username = #{username}")
    Integer getUserid(String username);
}

