package com.dabo.xunuo.base.entity;


/**
 * 用户凭证信息实体
 * Created by zhangbin on 16/7/31.
 */
public class UserCertificate {
    private long id;
    private long userId;
    private String password;//密码MD5
    private String salt;//密码盐
    private long updateTime;//最近一次修改时间

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
