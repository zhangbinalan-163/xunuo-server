package com.dabo.xunuo.web.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dabo.xunuo.common.Constants;
import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.entity.Contact;
import com.dabo.xunuo.entity.PageData;
import com.dabo.xunuo.entity.UserEvent;
import com.dabo.xunuo.entity.UserEventType;
import com.dabo.xunuo.service.IContactService;
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

    @Autowired
    private IContactService contactService;

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
                userEventTypeObject.put("type_id",userEventType.getId());
                userEventTypeObject.put("source_type",userEventType.getSourceType());
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
        jsonObject.put("type_id",userEventType.getId());
        return createSuccessResponse(jsonObject);
    }

    /**
     * 删除用户自定义的事件类型
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/type/delete")
    @ResponseBody
    public String deleteEventType() throws Exception {
        //当前登录userId
        long userId=RequestContext.getUserId();
        long typeId=RequestUtils.getLong(RequestContext.getNotEmptyParamMap(), "type_id");

        UserEventType userEventType = userEventService.getUserEventTypeById(typeId);
        if(userEventType==null){
            throw new SysException("请求数据不存在", Constants.ERROR_CODE_DATA_NOT_EXSIST);
        }
        if(userEventType.getSourceType()!=UserEventType.SOURCE_USER
                ||userEventType.getUserId()!=userId){
            throw new SysException("无权限",Constants.ERROR_CODE_DATA_NO_RIGHT);
        }
        //检查数量
        long eventCount=userEventService.countByEventType(typeId);
        if(eventCount>0){
            throw new SysException("有事件属于该类型,不允许删除",Constants.ERROR_CODE_HAS_DATA);
        }

        userEventService.deleteUserEventType(userEventType);

        return createDefaultSuccessResponse();
    }
    /**
     * 创建或者修改用户事件
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String updateEvent() throws Exception {
        //当前登录userId
        long userId=RequestContext.getUserId();
        //事件ID
        long eventId=RequestUtils.getLong(RequestContext.getNotEmptyParamMap(), "event_id", 0);
        //标题
        String eventName=RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "name");
        //时间
        Date eventDate = RequestUtils.getDate(RequestContext.getNotEmptyParamMap(), "event_time", "yyyy-MM-dd");
        //联系人
        long contactId=RequestUtils.getLong(RequestContext.getNotEmptyParamMap(), "contact_id", 0);
        //分类
        long eventTypeId=RequestUtils.getLong(RequestContext.getNotEmptyParamMap(), "event_type_id", 0);
        //提醒间隔
        int remindInterval=RequestUtils.getInt(RequestContext.getNotEmptyParamMap(), "remind_interval" , 0);
        //提醒间隔单位
        int remindIntervalType=RequestUtils.getInt(RequestContext.getNotEmptyParamMap(), "remind_interval_type" ,UserEvent.REMIND_INTERVAL_TYPE_DAY);
        //备注
        String remark=RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "remark" ,"");

        //参数的校验
        UserEvent userEvent=new UserEvent();
        userEvent.setId(eventId);
        userEvent.setUserId(userId);
        userEvent.setContactId(contactId);
        userEvent.setEventTime(eventDate.getTime());
        userEvent.setEventType(eventTypeId);
        userEvent.setRemindInterval(remindInterval);
        userEvent.setRemindIntervalUnit(remindIntervalType);
        userEvent.setRemark(remark);
        userEvent.setName(eventName);

        if(userEvent.getId()==0){
            //新建
            userEventService.createUserEvent(userEvent);
        }else{
            //修改
            UserEvent oldEvent = userEventService.getUserEventById(eventId);
            if(oldEvent==null){
                throw new SysException("数据不存在",Constants.ERROR_CODE_DATA_NOT_EXSIST);
            }
            if(oldEvent.getUserId()!=userId){
                throw new SysException("无权限",Constants.ERROR_CODE_DATA_NO_RIGHT);
            }

            userEventService.updateUserEvent(userEvent);
        }

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("event_id",userEvent.getId());
        return createSuccessResponse(jsonObject);
    }

    /**
     * 获取用户事件详情
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/info")
    @ResponseBody
    public String eventInfo() throws Exception {
        //当前登录userId
        long userId=RequestContext.getUserId();
        long eventId=RequestUtils.getLong(RequestContext.getNotEmptyParamMap(),"event_id");

        UserEvent eventInfo = userEventService.getUserEventById(eventId);
        if(eventInfo==null){
            throw new SysException("数据不存在",Constants.ERROR_CODE_DATA_NOT_EXSIST);
        }
        if(eventInfo.getUserId()!=userId){
            throw new SysException("无权限",Constants.ERROR_CODE_DATA_NO_RIGHT);
        }
        //
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("event_id",eventInfo.getId());
        jsonObject.put("name",eventInfo.getName());
        jsonObject.put("event_date",TimeUtils.getDateStrWithoutTime(eventInfo.getEventTime()));
        jsonObject.put("remark",eventInfo.getRemark());
        //联系人
        JSONObject contactObject=new JSONObject();
        long contactId=eventInfo.getContactId();
        Contact contactInfo = contactService.getContactById(contactId);
        if(contactInfo!=null){
            contactObject.put("contact_id",contactInfo.getId());
            contactObject.put("name",contactInfo.getName());
        }
        jsonObject.put("contact",contactObject);
        //类型
        long eventTypeId = eventInfo.getEventType();
        UserEventType eventTypeInfo = userEventService.getUserEventTypeById(eventTypeId);
        if(eventTypeInfo!=null){
            JSONObject typeObject=new JSONObject();
            typeObject.put("type_id",eventTypeInfo.getId());
            typeObject.put("type_name",eventTypeInfo.getName());
            jsonObject.put("event_type",typeObject);
        }
        //提醒
        JSONObject remindObject=new JSONObject();
        remindObject.put("remind_interval",eventInfo.getRemindInterval());
        remindObject.put("remind_interval_type",eventInfo.getRemindIntervalUnit());
        jsonObject.put("remind",remindObject);

        return createSuccessResponse(jsonObject);
    }

    /**
     * 获取最近的事件
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/nearly")
    @ResponseBody
    public String getNearlyByContact() throws Exception {
        //当前登录userId
        long contactId=RequestUtils.getLong(RequestContext.getNotEmptyParamMap(),"contact_id");

        UserEvent eventInfo = userEventService.getNeariestEventByContact(contactId);
        if(eventInfo==null){
            return createDefaultSuccessResponse();
        }
        //
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("event_id",eventInfo.getId());
        jsonObject.put("name",eventInfo.getName());
        jsonObject.put("event_date",TimeUtils.getDateStrWithoutTime(eventInfo.getEventTime()));
        jsonObject.put("remark",eventInfo.getRemark());
        //联系人
        JSONObject contactObject=new JSONObject();
        Contact contactInfo = contactService.getContactById(contactId);
        if(contactInfo!=null){
            contactObject.put("contact_id",contactInfo.getId());
            contactObject.put("name",contactInfo.getName());
        }
        jsonObject.put("contact",contactObject);
        //类型
        long eventTypeId = eventInfo.getEventType();
        UserEventType eventTypeInfo = userEventService.getUserEventTypeById(eventTypeId);
        if(eventTypeInfo!=null){
            JSONObject typeObject=new JSONObject();
            typeObject.put("type_id",eventTypeInfo.getId());
            typeObject.put("type_name",eventTypeInfo.getName());
            jsonObject.put("event_type",typeObject);
        }
        //提醒
        JSONObject remindObject=new JSONObject();
        remindObject.put("remind_interval",eventInfo.getRemindInterval());
        remindObject.put("remind_interval_type",eventInfo.getRemindIntervalUnit());
        jsonObject.put("remind",remindObject);

        return createSuccessResponse(jsonObject);
    }
}
