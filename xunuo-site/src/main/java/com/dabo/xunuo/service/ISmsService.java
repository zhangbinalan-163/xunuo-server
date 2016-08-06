package com.dabo.xunuo.service;

import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.entity.SmsCode;

/**
 * 短信验证码业务
 * Created by zhangbin on 16/8/5.
 */
public interface ISmsService {
    /**
     * 发送手机验证码
     * @param smsCode
     * @throws SysException
     */
    void sendSms(SmsCode smsCode) throws SysException;

    /**
     * 校验验证码
     * @param smsType
     * @param mobile
     * @throws SysException
     */
    void validSmsCode(int smsType,String mobile,String code) throws SysException;
}
