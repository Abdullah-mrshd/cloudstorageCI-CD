package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper
{
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> retrieve(Integer userid);

    @Select("SELECT key FROM CREDENTIALS where credentialId=#{credentialId}")
    String retrieveKey(Integer credentialId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    int delete(Integer credentialId);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, password=#{password} WHERE credentialId=#{credentialId}")
    int update(Integer credentialId, String url, String username, String password);
}
