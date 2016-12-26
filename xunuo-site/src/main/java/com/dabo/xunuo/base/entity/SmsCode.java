package com.dabo.xunuo.base.entity;

/**
 * 手机验证码
 * Created by zhangbin on 16/8/5.
 */
public class SmsCode {
    public static final int TYPE_REG=0;//注册时手机验证码
    public static final int TYPE_RESET_PASS=1;//重置密码发送手机验证码

    private long id;
    private int type;//类型
    private String mobile;
    private String code;//验证码
    private long createTime;//发送时间
    private long validInterval;//有效时间

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getValidInterval() {
        return validInterval;
    }

    public void setValidInterval(long validInterval) {
        this.validInterval = validInterval;
    }
}
