package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper
{
    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> retrieve(Integer userid);

    @Select("SELECT * FROM FILES WHERE userid = #{userid} AND filename = #{filename}")
    File getFile(Integer userid, String filename);

    @Delete("DELETE FROM FILES WHERE userid = #{userid} AND filename = #{filename}")
    int deleteFile(Integer userid, String filename);

    @Insert("INSERT INTO FILES (filename, contentType, fileSize, userid, fileData) VALUES(#{filename}, #{contentType}, #{fileSize}, #{userid}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);
}
