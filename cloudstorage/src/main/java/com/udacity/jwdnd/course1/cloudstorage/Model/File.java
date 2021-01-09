package com.udacity.jwdnd.course1.cloudstorage.Model;

import java.io.FileInputStream;


public class File
{
    private Integer fileId;
    private String filename;
    private String contentType;
    private long fileSize;
    private Integer userid;
    private byte[] fileData;

    public File(Integer fileId, String filename, String contentType, long fileSize, Integer userid, byte[] fileData) {
        this.fileId = fileId;
        this.filename = filename;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userid = userid;
        this.fileData = fileData;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public Integer getFileId() {
        return fileId;
    }

    public String getFilename() {
        return filename;
    }

    public String getContentType() {
        return contentType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public Integer getUserid() {
        return userid;
    }

    public byte[] getFileData() {
        return fileData;
    }
}
