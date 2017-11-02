package com.dabo.xunuo.base.service.impl;

import com.dabo.xunuo.base.common.Constants;
import com.dabo.xunuo.base.entity.Contact;
import com.dabo.xunuo.base.service.BaseSerivce;
import com.dabo.xunuo.base.service.IContactService;
import com.dabo.xunuo.base.service.IDeviceService;
import com.dabo.xunuo.base.service.ISmsService;
import com.dabo.xunuo.base.service.ISsoService;
import com.dabo.xunuo.base.service.IUserService;
import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.entity.SmsCode;
import com.dabo.xunuo.base.entity.User;
import com.dabo.xunuo.base.entity.UserCertificate;
import com.dabo.xunuo.base.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by zhangbin on 16/8/5.
 */
@Service
public class SsoServiceImpl extends BaseSerivce implements ISsoService {
    private static final long CODE_VALID_INTERVAL = 10 * 60 * 1000L;//有效期10分钟

    @Autowired
    private ISmsService smsService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private IContactService contactService;

    @Override
    public void sendRegCode(String mobile) throws SysException {
        //检查是否已经注册
        User user = userService.getByPhone(mobile);
        if (user != null) {
            throw new SysException("手机号已注册", Constants.ERROR_CODE_MOBILE_EXSIST);
        }
        //是否有验证码
        SmsCode oldSms = smsService.getSmsCode(SmsCode.TYPE_REG, mobile);
        if (oldSms != null) {
            smsService.deleteSmsCode(Arrays.asList(oldSms.getId()));
        }
        //生成验证码
        String code = StringUtils.genCode();
        //发送验证码
        SmsCode smsCode = new SmsCode();
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
        User user = userService.getByPhone(mobile);
        if (user == null) {
            throw new SysException("手机号未注册", Constants.ERROR_CODE_USER_NOTEXSIST);
        }
        //是否有验证码
        SmsCode oldSms = smsService.getSmsCode(SmsCode.TYPE_RESET_PASS, mobile);
        if (oldSms != null) {
            smsService.deleteSmsCode(Arrays.asList(oldSms.getId()));
        }
        //生成验证码
        String code = StringUtils.genCode();
        //发送验证码
        SmsCode smsCode = new SmsCode();
        smsCode.setCreateTime(System.currentTimeMillis());
        smsCode.setMobile(mobile);
        smsCode.setCode(code);
        smsCode.setType(SmsCode.TYPE_RESET_PASS);
        smsCode.setValidInterval(CODE_VALID_INTERVAL);
        smsService.sendSms(smsCode);
    }

    @Override
    public void login(String phone, String password, String deviceId, int deviceType) throws SysException {
        //检查密码
        User user = userService.getByPhone(phone);
        if (user == null) {
            throw new SysException("手机号未注册", Constants.ERROR_CODE_USER_NOTEXSIST);
        }
        UserCertificate userCertificate = userService.getUserCertificate(user);
        if (userCertificate == null) {
            throw new SysException("手机号未注册", Constants.ERROR_CODE_USER_NOTEXSIST);
        }
        String passWithSalt = StringUtils.md5(password + "#" + userCertificate.getSalt());
        String rightPass = userCertificate.getPassword();
        if (rightPass.equals(passWithSalt)) {
            //设备与账号绑定
            deviceService.userLogin(deviceId, deviceType, user.getId());
        } else {
            //todo 登录错误次数控制
            throw new SysException("密码错误", Constants.ERROR_CODE_PASS_ERROR);
        }
    }

    @Override
    public void regUser(String phone, String password, String code, String deviceId, int deviceType) throws SysException {
        //校验验证码
        smsService.validSmsCode(SmsCode.TYPE_REG, phone, code);
        //检查用户是否存在
        User userInfo = userService.getByPhone(phone);
        if (userInfo != null) {
            throw new SysException("手机号已注册", Constants.ERROR_CODE_MOBILE_EXSIST);
        }
        //保存用户信息
        long userId = userService.createUser(phone, password);
        //每个用户都默认生成两个联系人
        Contact fatherContact=new Contact();
        fatherContact.setName("爸爸");
        fatherContact.setUserId(userId);
        fatherContact.setFigureId(7);//写死是7
        contactService.createContact(fatherContact);
        Contact motherContact=new Contact();
        motherContact.setName("妈妈");
        motherContact.setUserId(userId);
        contactService.createContact(motherContact);
        //保存登录信息
        deviceService.userLogin(deviceId, deviceType, userId);
    }

    @Override
    public void resetPassword(String phone, String password, String code) throws SysException {
        //校验验证码
        smsService.validSmsCode(SmsCode.TYPE_RESET_PASS, phone, code);
        //检查用户是否存在
        User user = userService.getByPhone(phone);
        if (user == null) {
            throw new SysException("手机号未注册", Constants.ERROR_CODE_USER_NOTEXSIST);
        }
        userService.resetPassword(user, password);
    }

    @Override
    public void loginByOther(int sourceType, String accessToken, String openId, String deviceId, int deviceType) throws SysException {
        //todo 检查第三方access_token是否有效、是否与open_id对应
        //检查open_id是否存在
        User user = userService.getByOpenId(openId, sourceType);
        long userId = 0L;
        if (user == null) {
            //如果第一次登录,产生新账号、绑定OpenId
            userId = userService.createUser(sourceType, openId, accessToken);
        } else {
            userId = user.getId();
            if (!user.getAccessToken().equals(accessToken)) {
                //修改access_token
                userService.updateAccessToken(accessToken, userId);
            }
        }
        //登录
        deviceService.userLogin(deviceId, deviceType, userId);
    }

    @Override
    public void logout(String deviceId, int deviceType) throws SysException {
        deviceService.userLogin(deviceId, deviceType, 0);
    }
}
