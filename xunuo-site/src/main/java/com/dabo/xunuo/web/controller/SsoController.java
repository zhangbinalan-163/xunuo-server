package com.dabo.xunuo.web.controller;

import com.dabo.xunuo.service.ISsoService;
import com.dabo.xunuo.util.RequestUtils;
import com.dabo.xunuo.web.vo.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 注册登录相关的controller
 */
@RequestMapping("/sso")
@Controller
public class SsoController extends BaseController{

    @Autowired
    private ISsoService ssoService;

    /**
     * 注册时发手机验证码
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reg/code")
    @ResponseBody
    public String sendRegCode() throws Exception {
        //参数的解析与校验
        String phone=RequestUtils.getMobileString(RequestContext.getNotEmptyParamMap(), "phone");
        //发送注册验证码
        ssoService.sendRegCode(phone);
        //
        return createDefaultSuccessResponse();
    }

    /**
     * 注册用户
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reg")
    @ResponseBody
    public String regUser() throws Exception {
        String phone=RequestUtils.getMobileString(RequestContext.getNotEmptyParamMap(), "phone");
        String password=RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "password").toUpperCase();
        String code=RequestUtils.getString(RequestContext.getNotEmptyParamMap(),"code");
        String deviceId=RequestContext.getDeviceId();

        ssoService.regUser(phone,password,code,deviceId);

        return createDefaultSuccessResponse();
    }

    /**
     * 修改密码发手机验证码
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reset/code")
    @ResponseBody
    public String sendResetCode() throws Exception {
        //参数的解析与校验
        String phone=RequestUtils.getMobileString(RequestContext.getNotEmptyParamMap(), "phone");
        //发送验证码
        ssoService.sendResetCode(phone);
        //
        return createDefaultSuccessResponse();
    }

    /**
     * 修改密码
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reset")
    @ResponseBody
    public String resetPassword() throws Exception {
        //参数的解析与校验
        String phone=RequestUtils.getMobileString(RequestContext.getNotEmptyParamMap(), "phone");
        String password=RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "password").toUpperCase();
        String code=RequestUtils.getString(RequestContext.getNotEmptyParamMap(),"code");

        ssoService.resetPassword(phone,password,code);

        return createDefaultSuccessResponse();
    }

    /**
     * 修改密码发手机验证码
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public String login() throws Exception {
        //参数的解析与校验
        String phone=RequestUtils.getMobileString(RequestContext.getNotEmptyParamMap(), "phone");
        String password=RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "password").toUpperCase();
        //登录
        ssoService.login(phone, password, RequestContext.getDeviceId());

        return createDefaultSuccessResponse();
    }

    /**
     * 修改密码发手机验证码
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login/other")
    @ResponseBody
    public String loginByOther() throws Exception {
        //参数的解析与校验
        String openId=RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "open_id");
        String accessToken=RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "access_token");
        int sourceType=RequestUtils.getInt(RequestContext.getNotEmptyParamMap(), "source_type");
        //登录
        ssoService.loginByOther(sourceType,accessToken,openId,RequestContext.getDeviceId());

        return createDefaultSuccessResponse();
    }

    /**
     * 退出登录
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/logout")
    @ResponseBody
    public String logout() throws Exception {
        ssoService.logout(RequestContext.getDeviceId());
        return createDefaultSuccessResponse();
    }
}
