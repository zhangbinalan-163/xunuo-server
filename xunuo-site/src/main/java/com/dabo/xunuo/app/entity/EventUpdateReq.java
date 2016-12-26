/**
 * Copyright (C) 2006-2016 Tuniu All rights reserved
 */
package com.dabo.xunuo.app.entity;

/**
 * 事件修改请求
 * Date: 2016-12-21
 *
 * @author zhangbinalan
 */
public class EventUpdateReq {
    private long userId;
    private long eventId;
    private String eventName;
    private long eventTime;
    private int chineseCalendarFlag;
    private long contactId;
    private int eventClassId;
    private String eventClassName;
    private int remindInterval;
    private int remindIntervalType;
    private String remark;

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public int getChineseCalendarFlag() {
        return chineseCalendarFlag;
    }

    public void setChineseCalendarFlag(int chineseCalendarFlag) {
        this.chineseCalendarFlag = chineseCalendarFlag;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public int getEventClassId() {
        return eventClassId;
    }

    public void setEventClassId(int eventClassId) {
        this.eventClassId = eventClassId;
    }

    public String getEventClassName() {
        return eventClassName;
    }

    public void setEventClassName(String eventClassName) {
        this.eventClassName = eventClassName;
    }

    public int getRemindInterval() {
        return remindInterval;
    }

    public void setRemindInterval(int remindInterval) {
        this.remindInterval = remindInterval;
    }

    public int getRemindIntervalType() {
        return remindIntervalType;
    }

    public void setRemindIntervalType(int remindIntervalType) {
        this.remindIntervalType = remindIntervalType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
