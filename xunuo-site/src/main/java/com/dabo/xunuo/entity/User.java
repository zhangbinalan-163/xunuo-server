package com.dabo.xunuo.entity;

import java.util.Date;

/**
 * 用户信息实体
 * Created by zhangbin on 16/7/31.
 */
public class User {
    private long id;
    private String phone;
    private Date createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
