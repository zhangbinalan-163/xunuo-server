package com.dabo.xunuo.app.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.dabo.xunuo.app.web.vo.ClientType;
import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.service.IUserService;
import com.dabo.xunuo.base.entity.User;
import com.dabo.xunuo.base.service.ISsoService;
import com.dabo.xunuo.base.util.RequestUtils;
import com.dabo.xunuo.app.web.vo.RequestContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 注册登录相关的controller
 */
@RequestMapping("/sso")
@Controller
public class SsoController extends BaseController {

    @Autowired
    private ISsoService ssoService;

    @Autowired
    private IUserService userService;

    /**
     * 注册时发手机验证码
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reg/code")
    @ResponseBody
    public String sendRegCode() throws Exception {
        //参数的解析与校验
        String phone = RequestUtils.getMobileString(RequestContext.getNotEmptyParamMap(), "phone");
        //发送注册验证码
        ssoService.sendRegCode(phone);
        //
        return createDefaultSuccessResponse();
    }

    /**
     * 注册用户
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reg")
    @ResponseBody
    public String regUser() throws Exception {
        String phone = RequestUtils.getMobileString(RequestContext.getNotEmptyParamMap(), "phone");
        String password = RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "password").toUpperCase();
        String code = RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "code");
        String deviceId = RequestContext.getDeviceId();
        ClientType clientType = RequestContext.getClientType();

        ssoService.regUser(phone, password, code, deviceId, clientType.getId());

        return createSuccessResponse(getCurrentUser(phone));
    }

    /**
     * 修改密码发手机验证码
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reset/code")
    @ResponseBody
    public String sendResetCode() throws Exception {
        //参数的解析与校验
        String phone = RequestUtils.getMobileString(RequestContext.getNotEmptyParamMap(), "phone");
        //发送验证码
        ssoService.sendResetCode(phone);
        //
        return createDefaultSuccessResponse();
    }

    /**
     * 修改密码
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reset")
    @ResponseBody
    public String resetPassword() throws Exception {
        //参数的解析与校验
        String phone = RequestUtils.getMobileString(RequestContext.getNotEmptyParamMap(), "phone");
        String password = RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "password").toUpperCase();
        String code = RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "code");

        ssoService.resetPassword(phone, password, code);

        return createDefaultSuccessResponse();
    }

    /**
     * 修改密码发手机验证码
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public String login() throws Exception {
        ClientType clientType = RequestContext.getClientType();
        //参数的解析与校验
        String phone = RequestUtils.getMobileString(RequestContext.getNotEmptyParamMap(), "phone");
        String password = RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "password").toUpperCase();
        //登录
        ssoService.login(phone, password, RequestContext.getDeviceId(), clientType.getId());
        //
        return createSuccessResponse(getCurrentUser(phone));
    }

    private JSONObject getCurrentUser(String phone) throws SysException {
        JSONObject jsonObject = new JSONObject();
        User userInfo = userService.getByPhone(phone);
        if (userInfo != null) {
            jsonObject.put("id", userInfo.getId());
            jsonObject.put("user_name", userInfo.getPhone());
            jsonObject.put("nick_name", userInfo.getPhone());
            jsonObject.put("login_type", userInfo.getSource());
            jsonObject.put("head_url", "");
        }
        return jsonObject;
    }

    /**
     * 修改密码发手机验证码
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login/other")
    @ResponseBody
    public String loginByOther() throws Exception {
        ClientType clientType = RequestContext.getClientType();
        //参数的解析与校验
        String openId = RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "open_id");
        String accessToken = RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "access_token");
        int sourceType = RequestUtils.getInt(RequestContext.getNotEmptyParamMap(), "source_type");
        //登录
        ssoService.loginByOther(sourceType, accessToken, openId, RequestContext.getDeviceId(), clientType.getId());

        return createDefaultSuccessResponse();
    }

    /**
     * 退出登录
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/logout")
    @ResponseBody
    public String logout() throws Exception {
        ClientType clientType = RequestContext.getClientType();
        ssoService.logout(RequestContext.getDeviceId(), clientType.getId());
        return createDefaultSuccessResponse();
    }
}
