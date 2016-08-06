package com.dabo.xunuo.service.impl;

import com.dabo.xunuo.common.Constants;
import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.dao.SmsCodeMapper;
import com.dabo.xunuo.entity.SmsCode;
import com.dabo.xunuo.service.BaseSerivce;
import com.dabo.xunuo.service.ISmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Created by zhangbin on 16/8/5.
 */
@Service
public class SmsServiceImpl extends BaseSerivce implements ISmsService{

    @Autowired
    private SmsCodeMapper smsCodeMapper;

    @Override
    public void sendSms(SmsCode smsCode) throws SysException {
        //保存验证码
        smsCodeMapper.insert(smsCode);
        //TODO 调用第三方API发送验证码
    }

    @Override
    public void validSmsCode(int smsType, String mobile, String code) throws SysException {
        SmsCode smsCode = smsCodeMapper.getByMobile(smsType, mobile);
        if(smsCode!=null){
            long current=System.currentTimeMillis();
            if(code.equals(smsCode.getCode()) &&
                    (current - smsCode.getCreateTime() < smsCode.getValidInterval())){
                //删除验证码
                smsCodeMapper.delete(smsCode.getId());
                return;
            }
        }
        throw new SysException("验证码无效", Constants.ERROR_CODE_SMS_CODE_INVALID);
    }
}
