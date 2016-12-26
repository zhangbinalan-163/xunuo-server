package com.dabo.xunuo.base.service;

import com.dabo.xunuo.base.common.exception.SysException;

/**
 * 注册登录业务
 * Created by zhangbin on 16/8/5.
 */
public interface ISsoService {
    /**
     * 发送注册验证码
     * @param mobile
     * @throws SysException
     */
    void sendRegCode(String mobile) throws SysException;

    /**
     * 修改密码发送验证码
     * @param mobile
     * @throws SysException
     */
    void sendResetCode(String mobile) throws SysException;

    /**
     * 注册用户并登录
     * @param phone
     * @param password
     * @param code
     * @param deviceId
     * @throws SysException
     */
    void regUser(String phone,String password,String code,String deviceId) throws SysException;

    /**
     * 第三方账号登录
     * @param sourceType
     * @param accessToken
     * @param openId
     * @throws SysException
     */
    void loginByOther(int sourceType,String accessToken,String openId, String deviceId) throws SysException;

    /**
     * 用户名+密码登录
     * 与设备相关
     * @param phone
     * @param password
     * @param deviceId
     * @return
     * @throws SysException
     */
    void login(String phone,String password,String deviceId) throws SysException;

    /**
     * 修改密码
     * @param phone
     * @param password
     * @param code
     * @throws SysException
     */
    void resetPassword(String phone,String password,String code) throws SysException;

    /**
     * 退出登录
     * @param deviceId
     * @throws SysException
     */
    void logout(String deviceId) throws SysException;
}
