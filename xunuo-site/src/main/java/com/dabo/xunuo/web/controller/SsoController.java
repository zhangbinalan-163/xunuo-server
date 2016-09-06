package com.dabo.xunuo.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.dabo.xunuo.entity.User;
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
        //参数的解析与校验
        int sourceType=RequestUtils.getInt(RequestContext.getNotEmptyParamMap(), "source_type");
        if(sourceType== User.SOURCE_OUR){
            String phone=RequestUtils.getMobileString(RequestContext.getNotEmptyParamMap(), "phone");
            String password=RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "password").toUpperCase();
            String code=RequestUtils.getString(RequestContext.getNotEmptyParamMap(),"code");

            User user=new User();
            user.setPhone(phone);
            user.setCreateTime(System.currentTimeMillis());
            user.setSource(sourceType);
            user.setBindOpenId("");
            user.setPhone(phone);

            ssoService.regUser(user,password,code);

        }else if(sourceType == User.SOURCE_WECHAT||sourceType == User.SOURCE_WEIBO){
            String accessToken=RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "access_token");
            //TODO 根据token去获取用户的openid
        }
        //
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
        String sid=ssoService.login(phone, password, RequestContext.getDeviceId());

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("sid",sid);
        jsonObject.put("new_user",false);

        return createSuccessResponse(jsonObject);
    }
}
