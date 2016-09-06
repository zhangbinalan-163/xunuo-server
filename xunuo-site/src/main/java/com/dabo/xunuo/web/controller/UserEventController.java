package com.dabo.xunuo.web.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dabo.xunuo.entity.PageData;
import com.dabo.xunuo.entity.UserEvent;
import com.dabo.xunuo.entity.UserEventType;
import com.dabo.xunuo.service.IUserEventService;
import com.dabo.xunuo.util.RequestUtils;
import com.dabo.xunuo.util.StringUtils;
import com.dabo.xunuo.util.TimeUtils;
import com.dabo.xunuo.web.vo.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Time;
import java.util.Date;
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
                userEventObject.put("daysRemain", TimeUtils.getRemainDays(userEvent.getEventTime()));
                userEventObject.put("eventDate", TimeUtils.getDateStrWithoutTime(userEvent.getEventTime()));
                jsonArray.add(userEventObject);
            });
        }
        jsonObject.put("data",jsonArray);

        return createSuccessResponse(jsonObject);
    }

    /**
     * 获取当前用户的事件类型
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/type/list/byuser")
    @ResponseBody
    public String eventTypeListByUser() throws Exception {
        //当前登录userId
        long userId=RequestContext.getUserId();

        List<UserEventType> eventTypeList = userEventService.getUserEventType(userId);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("total",eventTypeList.size());

        JSONArray jsonArray=new JSONArray();
        if(!CollectionUtils.isEmpty(eventTypeList)){
            eventTypeList.forEach(userEventType -> {
                JSONObject userEventTypeObject=new JSONObject();
                userEventTypeObject.put("id",userEventType.getId());
                userEventTypeObject.put("sourceType",userEventType.getSourceType());
                userEventTypeObject.put("name",userEventType.getName());
                jsonArray.add(userEventTypeObject);
            });
        }
        jsonObject.put("data",jsonArray);

        return createSuccessResponse(jsonObject);
    }

    /**
     * 创建用户自定义的事件类型
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/type/create")
    @ResponseBody
    public String createEventType() throws Exception {
        //当前登录userId
        long userId=RequestContext.getUserId();
        String typeName=RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "name");

        UserEventType userEventType=new UserEventType();
        userEventType.setName(typeName);
        userEventType.setSourceType(UserEventType.SOURCE_USER);
        userEventType.setUserId(userId);

        userEventService.createUserEventType(userEventType);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("typeId",userEventType.getId());
        return createSuccessResponse(jsonObject);
    }

    /**
     * 创建用户事件
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/create")
    @ResponseBody
    public String createEvent() throws Exception {
        //当前登录userId
        long userId=RequestContext.getUserId();
        //标题
        String eventName=RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "name");
        //时间
        Date eventDate = RequestUtils.getDate(RequestContext.getNotEmptyParamMap(), "eventTime", "yyyy-MM-dd");
        //联系人
        long contactId=RequestUtils.getLong(RequestContext.getNotEmptyParamMap(), "contactId", 0);
        //分类
        long eventTypeId=RequestUtils.getLong(RequestContext.getNotEmptyParamMap(), "eventTypeId", 0);
        //提醒间隔
        int remindInterval=RequestUtils.getInt(RequestContext.getNotEmptyParamMap(), "remindInterval" , 0);
        //提醒间隔单位
        int remindIntervalType=RequestUtils.getInt(RequestContext.getNotEmptyParamMap(), "remindIntervalType" ,UserEvent.REMIND_INTERVAL_TYPE_DAY);
        //备注
        String remark=RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "remark" ,"");

        //TODO 参数的校验
        UserEvent userEvent=new UserEvent();
        userEvent.setUserId(userId);
        userEvent.setContactId(contactId);
        userEvent.setEventTime(eventDate.getTime());
        userEvent.setEventType(eventTypeId);
        userEvent.setRemindInterval(remindInterval);
        userEvent.setRemindIntervalUnit(remindIntervalType);
        userEvent.setRemark(remark);
        userEvent.setName(eventName);

        userEventService.createUserEvent(userEvent);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("eventId",userEvent.getId());
        return createSuccessResponse(jsonObject);
    }
}
