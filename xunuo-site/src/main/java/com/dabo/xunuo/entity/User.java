package com.dabo.xunuo.entity;


/**
 * 用户信息实体
 * Created by zhangbin on 16/7/31.
 */
public class User {
    //注册来源 0-手机+验证码
    public static final int SOURCE_OUR=0;
    //注册来源 微博
    public static final int SOURCE_WEIBO=2;
    //注册来源 微信
    public static final int SOURCE_WECHAT=1;

    private long id;
    private String phone;
    private int source;//注册来源 0-手机+验证码  1-微信 2-微博
    private String bindOpenId;//外部系统的openId source为1/2是有效
    private long createTime;

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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getBindOpenId() {
        return bindOpenId;
    }

    public void setBindOpenId(String bindOpenId) {
        this.bindOpenId = bindOpenId;
    }
}
