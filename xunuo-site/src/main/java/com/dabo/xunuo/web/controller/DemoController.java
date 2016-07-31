package com.dabo.xunuo.web.controller;


import com.dabo.xunuo.entity.User;
import com.dabo.xunuo.service.IUserService;
import com.dabo.xunuo.util.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息相关的controller
 */
@RequestMapping("/demo")
@Controller
public class DemoController extends BaseController{

    @Autowired
    private IUserService userService;

    /**
     * 测试
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/info")
    @ResponseBody
    public String userInfo(HttpServletRequest httpServletRequest) throws Exception {
        //参数的解析与校验
        String phone=RequestUtils.getString(httpServletRequest,"phone");
        //业务逻辑执行
        User user= userService.getByPhone(phone);
        //请求结果的拼装
        return createSuccessResponse(user);
    }
}
