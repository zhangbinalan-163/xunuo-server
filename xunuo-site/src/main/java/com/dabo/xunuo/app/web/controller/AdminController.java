package com.dabo.xunuo.app.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dabo.xunuo.base.dao.SmsCodeMapper;
import com.dabo.xunuo.base.entity.SmsCode;
import com.dabo.xunuo.base.entity.UserEventClass;
import com.dabo.xunuo.base.service.IUserEventService;
import com.dabo.xunuo.base.task.EventNextTimeTask;
import com.dabo.xunuo.base.task.EventNoticeTask;
import com.dabo.xunuo.base.util.JsonUtils;
import com.dabo.xunuo.base.util.StringUtils;
import com.dabo.xunuo.base.util.TimeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 后门相关接口
 * 线上环境不允许调用
 */
@RequestMapping("/admin")
@Controller
public class AdminController extends BaseController {

    @Autowired
    private SmsCodeMapper smsCodeMapper;

    @Autowired
    private IUserEventService userEventService;

    @Autowired
    private EventNoticeTask eventNoticeTask;


    @Autowired
    private EventNextTimeTask eventNextTimeTask;

    /**
     * 查询手机验证码
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/code/list")
    @ResponseBody
    public String getSmsCode(HttpServletRequest httpServletRequest) throws Exception {
        String phone = httpServletRequest.getParameter("mobile");
        if (!StringUtils.isEmpty(phone)) {
            JSONArray jsonArray = new JSONArray();
            //注册验证码
            SmsCode smsCode = smsCodeMapper.getByMobile(SmsCode.TYPE_REG, phone);
            if (smsCode != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", "注册账号验证码");
                jsonObject.put("phone", smsCode.getMobile());
                jsonObject.put("code", smsCode.getCode());
                jsonObject.put("validTime", TimeUtils.getStr(smsCode.getCreateTime() + smsCode.getValidInterval()));
                jsonArray.add(jsonObject);
            }
            return createSuccessResponse(jsonArray);
        }
        return createDefaultSuccessResponse();
    }

    /**
     * 创建系统自定义事件类型
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/event/type/create")
    @ResponseBody
    public String createEventType(HttpServletRequest httpServletRequest) throws Exception {
        String name = httpServletRequest.getParameter("name");

        UserEventClass userEventType = new UserEventClass();
        userEventType.setName(name);
        userEventType.setSourceType(UserEventClass.SOURCE_SYSTEM);

        userEventService.createUserEventClass(userEventType);

        return createDefaultSuccessResponse();
    }

    @RequestMapping(value = "/event/updateTime")
    @ResponseBody
    public String testNotice() throws Exception {
        eventNextTimeTask.run();
        return createDefaultSuccessResponse();
    }




    @RequestMapping(value = "/users-info/{uId}/avatar",method= RequestMethod.POST)
    @ResponseBody
    public String modifyUserAvatar(@RequestParam(value = "file") MultipartFile tmpFile, @PathVariable("uId") String uId, HttpServletRequest request, HttpServletResponse response)
    {
        String avatarUri;
        try{
            avatarUri= ImgUploadUtils.uploadUserAvatar(uId,tmpFile);
        }catch (Exception e){
            JSONObject respMap = new JSONObject();
            respMap.put("flag","false");
            respMap.put("msg",e.getMessage());
            return createSuccessResponse(respMap);
        }
        JSONObject respMap = new JSONObject();
        respMap.put("flag","true");
        respMap.put("msg",avatarUri);
        return createSuccessResponse(respMap);
    }

    /**
     * 文件上传的异常处理
     */
    @ExceptionHandler(value = {MaxUploadSizeExceededException.class})
    public void handleException(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Exception exception){
        if(exception instanceof MaxUploadSizeExceededException){
            JSONObject respMap = new JSONObject();
            respMap.put("flag","false");
            respMap.put("msg","too big");
            String jsonMessage = JsonUtils.fromObject(respMap);

            httpResponse.setContentType("application/json;charset=utf-8");
            try {
                httpResponse.getWriter().print(jsonMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
