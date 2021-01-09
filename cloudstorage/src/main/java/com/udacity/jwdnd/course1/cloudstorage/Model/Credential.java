package com.udacity.jwdnd.course1.cloudstorage.Model;

public class Credential
{
    private Integer credentialId;
    private String url;
    private String username;
    private String key;
    private String password;
    private int userid;


    public Credential()
    {
        super();
    }

    public Credential(Integer credentialId, String url, String username, String key, String password, int userid) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userid = userid;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getKey() {
        return key;
    }

    public String getPassword() {
        return password;
    }

    public Integer getUserid() {
        return userid;
    }
}
