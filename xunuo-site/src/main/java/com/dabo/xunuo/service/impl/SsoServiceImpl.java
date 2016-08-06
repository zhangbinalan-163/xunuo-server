package com.dabo.xunuo.service.impl;

import com.dabo.xunuo.common.Constants;
import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.entity.SidInfo;
import com.dabo.xunuo.entity.SmsCode;
import com.dabo.xunuo.entity.User;
import com.dabo.xunuo.entity.UserCertificate;
import com.dabo.xunuo.service.BaseSerivce;
import com.dabo.xunuo.service.ISmsService;
import com.dabo.xunuo.service.ISsoService;
import com.dabo.xunuo.service.IUserService;
import com.dabo.xunuo.util.SidUtils;
import com.dabo.xunuo.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangbin on 16/8/5.
 */
@Service
public class SsoServiceImpl extends BaseSerivce implements ISsoService{
    private static final long CODE_VALID_INTERVAL=10*60*1000L;//有效期10分钟

    @Autowired
    private ISmsService smsService;

    @Autowired
    private IUserService userService;

    @Override
    public void sendRegCode(String mobile) throws SysException {
        //生成验证码
        String code= StringUtils.genCode();
        //发送验证码
        SmsCode smsCode=new SmsCode();
        smsCode.setCreateTime(System.currentTimeMillis());
        smsCode.setMobile(mobile);
        smsCode.setCode(code);
        smsCode.setType(SmsCode.TYPE_REG);
        smsCode.setValidInterval(CODE_VALID_INTERVAL);
        smsService.sendSms(smsCode);
    }

    @Override
    public void sendResetCode(String mobile) throws SysException {
        //检查用户是否存在
        User user=userService.getByPhone(mobile);
        if(user==null){
            throw new SysException("手机号未注册",Constants.ERROR_CODE_USER_NOTEXSIST);
        }
        //TODO 通过缓存控制频率
        //生成验证码
        String code= StringUtils.genCode();
        //TODO 验证码也放到缓存
        //发送验证码
        SmsCode smsCode=new SmsCode();
        smsCode.setCreateTime(System.currentTimeMillis());
        smsCode.setMobile(mobile);
        smsCode.setCode(code);
        smsCode.setType(SmsCode.TYPE_RESET_PASS);
        smsCode.setValidInterval(CODE_VALID_INTERVAL);
        smsService.sendSms(smsCode);
    }

    @Override
    public User regUserByAccessToken(int sourceType, String accessToken) throws SysException {
        return null;
    }

    @Override
    public void regUser(User user, String password,String code) throws SysException {
        //校验验证码
        smsService.validSmsCode(SmsCode.TYPE_REG, user.getPhone(), code);

        //保存用户信息
        String salt=StringUtils.genCode();
        UserCertificate userCertificate=new UserCertificate();
        userCertificate.setPassword(StringUtils.md5(password+"#"+salt));
        userCertificate.setSalt(salt);
        userCertificate.setUserId(user.getId());
        userCertificate.setUpdateTime(System.currentTimeMillis());

        userService.createUser(user,userCertificate);
    }

    @Override
    public String login(String phone, String password, String deviceId) throws SysException {
        //检查密码
        User user=userService.getByPhone(phone);
        if(user==null){
            throw new SysException("手机号未注册",Constants.ERROR_CODE_USER_NOTEXSIST);
        }
        UserCertificate userCertificate = userService.getUserCertificate(user);
        if(userCertificate==null){
            throw new SysException("手机号未注册",Constants.ERROR_CODE_USER_NOTEXSIST);
        }
        String passWithSalt=StringUtils.md5(password+"#"+userCertificate.getSalt());
        String rightPass=userCertificate.getPassword();
        if(rightPass.equals(passWithSalt)){
            //登录成功,生成SID
            return generateSid(user.getId(), deviceId);
        }else{
            //登录错误次数控制
            throw new SysException("密码错误",Constants.ERROR_CODE_PASS_ERROR);
        }
    }

    private String generateSid(long id, String deviceId) throws SysException {
        SidInfo sidInfo=new SidInfo();
        sidInfo.setLoginTime(System.currentTimeMillis());
        sidInfo.setDeviceId(deviceId);
        sidInfo.setUserId(id);

        return SidUtils.generateSid(sidInfo);
    }
}
