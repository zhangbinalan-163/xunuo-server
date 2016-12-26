package com.dabo.xunuo.app.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dabo.xunuo.base.dao.SmsCodeMapper;
import com.dabo.xunuo.base.entity.SmsCode;
import com.dabo.xunuo.base.entity.UserEventClass;
import com.dabo.xunuo.base.service.IUserEventService;
import com.dabo.xunuo.base.util.StringUtils;
import com.dabo.xunuo.base.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 后门相关接口
 * 线上环境不允许调用
 */
@RequestMapping("/admin")
@Controller
public class  AdminController extends BaseController{

    @Autowired
    private SmsCodeMapper smsCodeMapper;

    @Autowired
    private IUserEventService userEventService;

    /**
     * 查询手机验证码
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/code/list")
    @ResponseBody
    public String getSmsCode(HttpServletRequest httpServletRequest) throws Exception {
        String phone=httpServletRequest.getParameter("mobile");
        if(!StringUtils.isEmpty(phone)){
            JSONArray jsonArray=new JSONArray();
            //注册验证码
            SmsCode smsCode = smsCodeMapper.getByMobile(SmsCode.TYPE_REG, phone);
            if(smsCode!=null){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("type","注册账号验证码");
                jsonObject.put("phone",smsCode.getMobile());
                jsonObject.put("code",smsCode.getCode());
                jsonObject.put("validTime", TimeUtils.getStr(smsCode.getCreateTime()+smsCode.getValidInterval()));
                jsonArray.add(jsonObject);
            }
            return createSuccessResponse(jsonArray);
        }
        return createDefaultSuccessResponse();
    }

    /**
     * 创建系统自定义事件类型
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/event/type/create")
    @ResponseBody
    public String createEventType(HttpServletRequest httpServletRequest) throws Exception {
        String name=httpServletRequest.getParameter("name");

        UserEventClass userEventType=new UserEventClass();
        userEventType.setName(name);
        userEventType.setSourceType(UserEventClass.SOURCE_SYSTEM);

        userEventService.createUserEventClass(userEventType);

        return createDefaultSuccessResponse();
    }

}
