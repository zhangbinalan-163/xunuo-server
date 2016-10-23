package com.dabo.xunuo.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.dabo.xunuo.entity.User;
import com.dabo.xunuo.service.IUserService;
import com.dabo.xunuo.util.TimeUtils;
import com.dabo.xunuo.web.vo.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户相关的controller
 */
@RequestMapping("/user")
@Controller
public class UserController extends BaseController{

    @Autowired
    private IUserService userService;

    /**
     * 获取当前用户信息
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/info")
    @ResponseBody
    public String userInfo() throws Exception {
        //当前登录userId
        long userId=RequestContext.getUserId();
        //查询用户信息
        User userInfo = userService.getByUserId(userId);
        JSONObject jsonObject=new JSONObject();
        if(userInfo!=null){
            jsonObject.put("user_id",userInfo.getId());
            jsonObject.put("source_type",userInfo.getSource());
            jsonObject.put("open_id",userInfo.getBindOpenId());
            jsonObject.put("create_time", TimeUtils.getStr(userInfo.getCreateTime()));
        }
        return createSuccessResponse(jsonObject);
    }
}
