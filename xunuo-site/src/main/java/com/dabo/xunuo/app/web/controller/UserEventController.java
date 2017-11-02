package com.dabo.xunuo.app.web.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dabo.xunuo.app.entity.EventListByUserResponse;
import com.dabo.xunuo.app.entity.EventUpdateReq;
import com.dabo.xunuo.app.service.AppEventService;
import com.dabo.xunuo.app.web.vo.RequestContext;
import com.dabo.xunuo.base.common.Constants;
import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.entity.Contact;
import com.dabo.xunuo.base.entity.UserEvent;
import com.dabo.xunuo.base.entity.UserEventClass;
import com.dabo.xunuo.base.service.IContactService;
import com.dabo.xunuo.base.service.IUserEventService;
import com.dabo.xunuo.base.util.RequestUtils;

/**
 * 用户事件相关的controller
 */
@RequestMapping("/event")
@Controller
public class UserEventController extends BaseController {

    @Autowired
    private IUserEventService userEventService;

    @Autowired
    private AppEventService appEventService;

    @Autowired
    private IContactService contactService;

    /**
     * 获取当前用户的事件列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String deleteEvent() throws Exception {
        //当前登录userId
        long userId = RequestContext.getUserId();
        long eventId = RequestUtils.getLong(RequestContext.getNotEmptyParamMap(), "event_id");

        UserEvent eventInfo = userEventService.getUserEventById(eventId);
        if (eventInfo == null) {
            throw new SysException("数据不存在", Constants.ERROR_CODE_DATA_NOT_EXSIST);
        }
        if (eventInfo.getUserId() != userId) {
            throw new SysException("无权限", Constants.ERROR_CODE_DATA_NO_RIGHT);
        }
        userEventService.deleteUserEvent(eventInfo);
        return createDefaultSuccessResponse();
    }

    /**
     * 获取当前用户的事件列表
     * 按照下一次发生事件排序
     */
    @RequestMapping(value = "/list/byuser")
    @ResponseBody
    public String eventListByUser() throws Exception {
        //当前登录userId
        long userId = RequestContext.getUserId();
        int page = RequestUtils.getInt(RequestContext.getNotEmptyParamMap(), "page", 1);
        int limit = RequestUtils.getInt(RequestContext.getNotEmptyParamMap(), "limit", 10);

        EventListByUserResponse response = appEventService.getEventList(userId, page, limit);

        return createSuccessResponse(response);
    }

    /**
     * 获取联系人的事件
     * 按照下一次发生事件排序
     */
    @RequestMapping(value = "/list/bycontact")
    @ResponseBody
    public String eventListByContact() throws Exception {
        //当前登录userId
        long userId = RequestContext.getUserId();
        long contact_id = RequestUtils.getLong(RequestContext.getNotEmptyParamMap(), "contact_id");
        Contact contactInfo = contactService.getContactById(contact_id);
        if(contactInfo==null){
            throw new SysException("数据不存在", Constants.ERROR_CODE_DATA_NOT_EXSIST);
        }
        if(contactInfo.getUserId()!=userId){
            throw new SysException("无权限", Constants.ERROR_CODE_DATA_NO_RIGHT);
        }
        int page = RequestUtils.getInt(RequestContext.getNotEmptyParamMap(), "page", 1);
        int limit = RequestUtils.getInt(RequestContext.getNotEmptyParamMap(), "limit", 10);

        EventListByUserResponse response = appEventService.getEventListByContact(contact_id, page, limit);

        return createSuccessResponse(response);
    }
    /**
     * 获取当前用户的事件类型
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/class/list/byuser")
    @ResponseBody
    public String eventClassListByUser() throws Exception {
        //当前登录userId
        long userId = RequestContext.getUserId();

        List<UserEventClass> eventTypeList = userEventService.getUserEventClass(userId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", eventTypeList.size());

        JSONArray jsonArray = new JSONArray();
        if (!CollectionUtils.isEmpty(eventTypeList)) {
            eventTypeList.forEach(userEventType -> {
                JSONObject userEventTypeObject = new JSONObject();
                userEventTypeObject.put("event_class_id", userEventType.getId());
                userEventTypeObject.put("source_type", userEventType.getSourceType());
                userEventTypeObject.put("name", userEventType.getName());
                userEventTypeObject.put("bigday_flag", userEventType.getBigDayFlag());
                jsonArray.add(userEventTypeObject);
            });
        }
        jsonObject.put("data", jsonArray);

        return createSuccessResponse(jsonObject);
    }

    /**
     * 创建或者修改用户事件
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String updateEvent() throws Exception {
        //当前登录userId
        long userId = RequestContext.getUserId();
        //事件ID
        long eventId = RequestUtils.getLong(RequestContext.getNotEmptyParamMap(), "event_id", 0);
        //标题
        String eventName = RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "name");
        //时间
        long eventTime = RequestUtils.getLong(RequestContext.getNotEmptyParamMap(), "event_time");
        //时间
        int chineseCalendarFlag = RequestUtils.getInt(RequestContext.getNotEmptyParamMap(), "chinese_calendar_flag");
        //联系人
        long contactId = RequestUtils.getLong(RequestContext.getNotEmptyParamMap(), "contact_id", 0);
        //分类
        int eventClassId = RequestUtils.getInt(RequestContext.getNotEmptyParamMap(), "event_class_id", 0);
        //标题
        String eventClassName = RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "event_class_name", "");
        //提醒间隔
        int remindInterval = RequestUtils.getInt(RequestContext.getNotEmptyParamMap(), "remind_interval", 0);
        //提醒间隔单位
        int remindIntervalType = RequestUtils.getInt(RequestContext.getNotEmptyParamMap(), "remind_interval_type", UserEvent.REMIND_INTERVAL_TYPE_DAY);
        //备注
        String remark = RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "remark", "");
        //参数的校验
        EventUpdateReq userEventReq = new EventUpdateReq();
        userEventReq.setEventId(eventId);
        userEventReq.setUserId(userId);
        userEventReq.setContactId(contactId);
        userEventReq.setEventTime(eventTime);
        userEventReq.setEventClassId(eventClassId);
        userEventReq.setEventClassName(eventClassName);
        userEventReq.setChineseCalendarFlag(chineseCalendarFlag);
        userEventReq.setRemindInterval(remindInterval);
        userEventReq.setRemindIntervalType(remindIntervalType);
        userEventReq.setRemark(remark);
        userEventReq.setEventName(eventName);

        appEventService.updateEvent(userEventReq);

        return createDefaultSuccessResponse();
    }

    /**
     * 获取用户事件详情
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/info")
    @ResponseBody
    public String eventInfo() throws Exception {
        //当前登录userId
        long userId = RequestContext.getUserId();
        long eventId = RequestUtils.getLong(RequestContext.getNotEmptyParamMap(), "event_id");

        UserEvent eventInfo = userEventService.getUserEventById(eventId);
        if (eventInfo == null) {
            throw new SysException("数据不存在", Constants.ERROR_CODE_DATA_NOT_EXSIST);
        }
        if (eventInfo.getUserId() != userId) {
            throw new SysException("无权限", Constants.ERROR_CODE_DATA_NO_RIGHT);
        }
        return createDefaultSuccessResponse();
    }
}
