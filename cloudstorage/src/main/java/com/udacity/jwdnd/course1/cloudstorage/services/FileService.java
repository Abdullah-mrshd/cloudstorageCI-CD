package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService
{
    private final FileMapper fileMapper;
    private final UserService userService;

    public FileService(FileMapper fileMapper, UserService userService)
    {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    // Retrieves user's files under his username
    public List<File> getFiles(String username)
    {
        return fileMapper.retrieve(userService.getUserid(username));
    }

    // Check file name availability for a defined user
    public boolean isFileNameAvailable(String filename, String username)
    {
        List<File> userFiles = getFiles(username);
        for (File file : userFiles)
        {
            if( filename.equals(file.getFilename()))
                return false;
        }
        return true;
    }

    // Retrieves a file instance by its name
    public File getFile(String username, String filename)
    {
        Integer userid = userService.getUserid(username);
        return fileMapper.getFile(userid, filename);
    }

    // Upload the file to the database and relates to a specific user
    public int uploadFile(MultipartFile file, String username) throws IOException
    {
        int userid = userService.getUserid(username);
        return fileMapper.insert( new File(null, file.getOriginalFilename(), file.getContentType(),
                file.getSize(),userid, file.getBytes()));
    }

    // Delete file from database for a defined user
    public int removeFile(String username, String filename)
    {
        Integer userid = userService.getUserid(username);
        return fileMapper.deleteFile(userid, filename);
    }
}
