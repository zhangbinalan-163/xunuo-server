package com.dabo.xunuo.web.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dabo.xunuo.entity.PageData;
import com.dabo.xunuo.entity.UserEvent;
import com.dabo.xunuo.service.IUserEventService;
import com.dabo.xunuo.util.RequestUtils;
import com.dabo.xunuo.web.vo.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 用户事件相关的controller
 */
@RequestMapping("/event")
@Controller
public class UserEventController extends BaseController{

    @Autowired
    private IUserEventService userEventService;

    /**
     * 获取当前用户的事件列表
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list/byuser")
    @ResponseBody
    public String eventListByUser() throws Exception {
        //当前登录userId
        long userId=RequestContext.getUserId();
        int page= RequestUtils.getInt(RequestContext.getNotEmptyParamMap(), "page",1);
        int limit=RequestUtils.getInt(RequestContext.getNotEmptyParamMap(), "limit",10);

        PageData<UserEvent> pageData = userEventService.getUserEvent(userId, page, limit);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("total",pageData.getTotal());
        List<UserEvent> dataList = pageData.getData();
        JSONArray jsonArray=new JSONArray();
        if(!CollectionUtils.isEmpty(dataList)){
            dataList.forEach(userEvent -> {
                JSONObject userEventObject=new JSONObject();
                userEventObject.put("id",userEvent.getId());
                userEventObject.put("name",userEvent.getName());
                //TODO
                userEventObject.put("daysRemain",userEvent.getId());
                userEventObject.put("eventDate",userEvent.getId());
                jsonArray.add(userEventObject);
            });
        }
        jsonObject.put("data",jsonArray);

        return createSuccessResponse(jsonObject);
    }
}
