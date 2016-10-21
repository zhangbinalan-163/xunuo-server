package com.dabo.xunuo.service.impl;

import com.dabo.xunuo.common.Constants;
import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.dao.SmsCodeMapper;
import com.dabo.xunuo.entity.SmsCode;
import com.dabo.xunuo.service.BaseSerivce;
import com.dabo.xunuo.service.ISmsService;
import com.dabo.xunuo.util.JsonUtils;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短信验证码业务
 * Created by zhangbin on 16/8/5.
 */
@Service
public class SmsServiceImpl extends BaseSerivce implements ISmsService{
    private static Logger LOG= LoggerFactory.getLogger(SmsServiceImpl.class);

    private String aliSmsSendUrl="http://gw.api.taobao.com/router/rest";

    private String aliAppKey="23488349";

    private String aliAppSecret="655cade0eaa3e32e34366bc833a546ff";

    @Autowired
    private SmsCodeMapper smsCodeMapper;

    @Override
    public void sendSms(SmsCode smsCode) throws SysException {
        //保存验证码
        smsCodeMapper.insert(smsCode);
        //实际发送短信验证码
        sendSmsByAli(smsCode.getMobile(),smsCode.getCode(),smsCode.getValidInterval());
    }

    /**
     * 阿里大于发送验证码
     * @param mobile
     * @param code
     */
    private void sendSmsByAli(String mobile, String code,long timeInterval) throws SysException{
        TaobaoClient client = new DefaultTaobaoClient(aliSmsSendUrl, aliAppKey, aliAppSecret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setSmsType("normal");
        req.setSmsFreeSignName("许诺");
        req.setSmsParamString("{\"code\":\""+code+"\",\"time\":\""+(timeInterval/60000)+"\"}");
        req.setRecNum(mobile);
        req.setSmsTemplateCode("SMS_21365018");
        AlibabaAliqinFcSmsNumSendResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            LOG.error("send sms by ali fail,mobile={}",mobile,e);
            throw new SysException("短信发送失败");
        }
        if(rsp==null||!rsp.isSuccess()){
            LOG.error("send sms by ali fail,mobile={},resp={}",mobile, JsonUtils.fromObject(rsp));
            throw new SysException("短信发送失败");
        }
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

    @Override
    public List<Long> getInvalidSmsCodeId() throws SysException {
        //获取当前时间已经失效的验证码
        long currentTime = System.currentTimeMillis();
        return smsCodeMapper.getByValidTime(currentTime);
    }

    @Override
    public void deleteSmsCode(List<Long> codeIdList) throws SysException {
        smsCodeMapper.deleteBatch(codeIdList);
    }

    @Override
    public SmsCode getSmsCode(int smsType, String mobile) throws SysException {
        SmsCode smsCode = smsCodeMapper.getByMobile(smsType, mobile);
        return smsCode;
    }

}
