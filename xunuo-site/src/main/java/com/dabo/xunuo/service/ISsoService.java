package com.dabo.xunuo.service;

import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.entity.User;

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
     * 注册用户
     * @param user
     * @param password
     * @param code
     * @throws SysException
     */
    void regUser(User user,String password,String code) throws SysException;

    /**
     *
     * @param sourceType
     * @param accessToken
     * @throws SysException
     */
    User regUserByAccessToken(int sourceType,String accessToken) throws SysException;

    /**
     * 用户名+密码登录
     * 与设备相关
     * @param phone
     * @param password
     * @param deviceId
     * @return
     * @throws SysException
     */
    String login(String phone,String password,String deviceId) throws SysException;
}
