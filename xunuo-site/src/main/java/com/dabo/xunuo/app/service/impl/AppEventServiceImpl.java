/**
 * Copyright (C) 2006-2016 Tuniu All rights reserved
 */
package com.dabo.xunuo.app.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dabo.xunuo.app.entity.EventInfoData;
import com.dabo.xunuo.app.entity.EventListByUserResponse;
import com.dabo.xunuo.app.entity.EventUpdateReq;
import com.dabo.xunuo.app.service.AppEventService;
import com.dabo.xunuo.base.common.Constants;
import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.entity.Contact;
import com.dabo.xunuo.base.entity.PageData;
import com.dabo.xunuo.base.entity.UserEvent;
import com.dabo.xunuo.base.entity.UserEventClass;
import com.dabo.xunuo.base.entity.UserEventNextTrigger;
import com.dabo.xunuo.base.service.IContactService;
import com.dabo.xunuo.base.service.IUserEventService;
import com.dabo.xunuo.base.util.StringUtils;
import com.dabo.xunuo.base.util.TimeUtils;

@Service
public class AppEventServiceImpl implements AppEventService {
    @Autowired
    private IUserEventService userEventService;

    @Autowired
    private IContactService contactService;

    @Override
    public void updateEvent(EventUpdateReq eventUpdateReq) throws SysException {
        UserEvent userEvent = new UserEvent();
        userEvent.setId(eventUpdateReq.getEventId());
        userEvent.setUserId(eventUpdateReq.getUserId());
        userEvent.setContactId(eventUpdateReq.getContactId());
        userEvent.setEventTime(eventUpdateReq.getEventTime());
        userEvent.setEventClass(eventUpdateReq.getEventClassId());
        userEvent.setChineseCalendarFlag(eventUpdateReq.getChineseCalendarFlag());
        userEvent.setRemindInterval(eventUpdateReq.getRemindInterval());
        userEvent.setRemindIntervalUnit(eventUpdateReq.getRemindIntervalType());
        userEvent.setRemark(eventUpdateReq.getRemark());
        userEvent.setName(eventUpdateReq.getEventName());

        int eventClassId = eventUpdateReq.getEventClassId();
        UserEventClass eventClass;
        if (eventUpdateReq.getEventClassId() == 0 && !StringUtils.isEmpty(eventUpdateReq.getEventClassName())) {
            //新建自定义的事件类型
            eventClass = userEventService.createUserEventClass(userEvent.getUserId(), eventUpdateReq.getEventClassName(), eventUpdateReq.getEventTime());
            eventClassId = eventClass.getId();
        } else {
            eventClass = userEventService.getEventClass(eventClassId);
        }
        userEvent.setEventClass(eventClassId);
        userEvent.setUserEventClass(eventClass);

        if (userEvent.getId() == 0) {
            //新建
            userEventService.createUserEvent(userEvent);
        } else {
            //修改
            UserEvent oldEvent = userEventService.getUserEventById(eventUpdateReq.getEventId());
            if (oldEvent == null) {
                throw new SysException("数据不存在", Constants.ERROR_CODE_DATA_NOT_EXSIST);
            }
            if (oldEvent.getUserId() != userEvent.getUserId()) {
                throw new SysException("无权限", Constants.ERROR_CODE_DATA_NO_RIGHT);
            }
            userEventService.updateUserEvent(userEvent, oldEvent);
        }
    }

    @Override
    public EventListByUserResponse getEventList(long userId, int page, int limit) throws SysException {
        EventListByUserResponse response = new EventListByUserResponse();
        //从下一次发生时间表中进行排序过滤
        PageData<UserEventNextTrigger> pageData = userEventService.getEventNextTriggerList(userId, page, limit);
        response.setTotal(pageData.getTotal());

        List<EventInfoData> infoList = new ArrayList<>();
        List<UserEventNextTrigger> dataList = pageData.getData();
        if (!CollectionUtils.isEmpty(dataList)) {
            List<Long> eventIdList = new ArrayList<>();
            dataList.forEach(userEventNextTrigger -> eventIdList.add(userEventNextTrigger.getEventId()));
            Map<Long, UserEvent> eventMap = userEventService.getUserEventByIds(eventIdList);
            //设置联系人
            List<Long> contactIdList = new ArrayList<>();
            List<Integer> userEventTypeList = new ArrayList<>();
            eventMap.entrySet().forEach(entry -> {
                if (entry != null && entry.getValue() != null && entry.getValue().getContactId() != 0) {
                    contactIdList.add(entry.getValue().getContactId());
                }
                if (entry != null && entry.getValue() != null && entry.getValue().getEventClass() != 0) {
                    userEventTypeList.add(entry.getValue().getEventClass());
                }
            });
            Map<Long, Contact> contactId2InfoMap = contactService.getContactMapByIds(contactIdList);
            //设置事件类别
            dataList.forEach(userEventNextTrigger -> {
                UserEvent userEvent = eventMap.get(userEventNextTrigger.getEventId());
                if (userEvent != null) {
                    EventInfoData eventInfoData = parseEventInfoData(userEvent, userEventNextTrigger.getTriggerTime(), contactId2InfoMap.get(userEvent.getContactId()), userEvent.getUserEventClass());
                    infoList.add(eventInfoData);
                }
            });
        }
        response.setData(infoList);
        return response;
    }

    private EventInfoData parseEventInfoData(UserEvent userEvent, long triggerTime, Contact contact, UserEventClass eventClass) {
        EventInfoData eventInfoData = new EventInfoData();
        eventInfoData.setChinese_calendar_flag(userEvent.getChineseCalendarFlag());
        eventInfoData.setDays_remain(TimeUtils.getRemainDays(triggerTime));
        eventInfoData.setEvent_id(userEvent.getId());
        eventInfoData.setEvent_time(userEvent.getEventTime());
        eventInfoData.setName(userEvent.getName());
        eventInfoData.setRemark(userEvent.getRemark());
        eventInfoData.setRemind_interval(userEvent.getRemindInterval());
        eventInfoData.setRemind_interval_type(userEvent.getRemindIntervalUnit());
        if (eventClass != null) {
            EventInfoData.EventClassInfo eventClassData = new EventInfoData.EventClassInfo();
            eventClassData.setName(eventClass.getName());
            eventClassData.setBigday_flag(eventClass.getBigDayFlag());
            eventClassData.setEvent_class_id(eventClass.getClassType());
            eventClassData.setSource_type(eventClass.getSourceType());
            eventInfoData.setEvent_class(eventClassData);
        }
        if (contact != null) {
            EventInfoData.ContactInfoData contactInfo = new EventInfoData.ContactInfoData();
            contactInfo.setName(contact.getName());
            contactInfo.setContact_id(contact.getId());
            contactInfo.setHead_url(contact.getHeadImg());
            eventInfoData.setContactInfo(contactInfo);
        }
        return eventInfoData;
    }
}
