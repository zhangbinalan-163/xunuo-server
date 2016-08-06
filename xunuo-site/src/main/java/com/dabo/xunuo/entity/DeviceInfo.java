package com.dabo.xunuo.entity;

/**
 * 终端设备信息
 * Created by zhangbin on 16/8/5.
 */
public class DeviceInfo {
    //其他字段均可扩展
    private long id;
    private String deviceId;
    private long createTime;

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
}
