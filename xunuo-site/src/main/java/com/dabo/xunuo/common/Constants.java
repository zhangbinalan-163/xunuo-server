package com.dabo.xunuo.common;

/**
 * 一些常量定义
 */
public class Constants {
    //默认错误码
    public static final int DEFAULT_CODE_ERROR=-1;
    //正常请求码
    public static final int DEFAULT_CODE_SUCCESS=0;
    //正常请求码
    public static final String DEFAULT_MSG_SUCCESS="success";
    //异常请求默认信息
    public static final String DEFAULT_MSG_ERROR="服务器异常";

    //错误码,参数格式错误
    public static final int ERROR_CODE_INVALID_PARAM=40005;
    //错误码,签名错误
    public static final int ERROR_CODE_INVALID_SIGN=40002;
    //错误码,设备已经注册错误
    public static final int ERROR_CODE_DEVICE_EXSIST=40006;
    //错误码,验证码无效
    public static final int ERROR_CODE_SMS_CODE_INVALID=40007;
    //错误码,账号不存在
    public static final int ERROR_CODE_USER_NOTEXSIST=40008;
    //错误码,密码错误
    public static final int ERROR_CODE_PASS_ERROR=40009;
    //错误码,未登录
    public static final int ERROR_CODE_NOT_LOGIN=40010;

    //APP的KEY,签名会用
    public static final String APP_KEY="dabo_app_xunuo";
    //APP的SECRET,签名会用
    public static final String APP_SECRET="f141c6795225cae6014228e42d13bbc";


    //AES加密解密密钥
    public static final String SECRET_KEY="f141c6795225cae6014b228e42d13bbc";


}
