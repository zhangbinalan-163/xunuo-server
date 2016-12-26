package com.dabo.xunuo.base.entity;

/**
 * 终端设备信息
 * Created by zhangbin on 16/8/5.
 */
public class DeviceInfo {
    private long id;
    private String deviceId;
    private long createTime;
    private long loginUserId;//当前登录的用户
    private long loginTime;//用户最近登录时间

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(long loginUserId) {
        this.loginUserId = loginUserId;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }
}
