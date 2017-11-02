package com.dabo.xunuo.base.entity;

import java.sql.Time;
import java.util.Date;

import com.dabo.xunuo.base.util.TimeUtils;

/**
 * 用户事件实体
 * Created by zhangbin on 16/8/28.
 */
public class UserEvent {

    public static final int REMIND_INTERVAL_TYPE_DAY = 0;
    public static final int REMIND_INTERVAL_TYPE_WEEK = 1;

    public static final int CHINESE_CALENDAR_YES = 1;
    public static final int CHINESE_CALENDAR_NO = 0;
    public static final int STATE_NORMAL = 0;
    public static final int STATE_DELETE = 1;
    private long id;
    private long userId;//事件的所有者
    private String name;
    private long contactId;//事件关联的联系人
    private long createTime;
    private long updateTime;
    private long eventTime;
    private int eventClass;
    private UserEventClass userEventClass;
    private int chineseCalendarFlag;
    private int remindInterval;
    private int remindIntervalUnit;
    private String remark;
    private int stateFlag;
    private long nextEventTime;

    private String timeDesc;

    public String getTimeDesc() {
        timeDesc="eventTime=" + TimeUtils.getStr(eventTime) + ",nextEventTime=" + TimeUtils.getStr(nextEventTime);
        return timeDesc;
    }


    public long getNextEventTime() {
        return nextEventTime;
    }

    public void setNextEventTime(long nextEventTime) {
        this.nextEventTime = nextEventTime;
    }

    public int getStateFlag() {
        return stateFlag;
    }

    public void setStateFlag(int stateFlag) {
        this.stateFlag = stateFlag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public int getEventClass() {
        return eventClass;
    }

    public void setEventClass(int eventClass) {
        this.eventClass = eventClass;
    }

    public int getChineseCalendarFlag() {
        return chineseCalendarFlag;
    }

    public void setChineseCalendarFlag(int chineseCalendarFlag) {
        this.chineseCalendarFlag = chineseCalendarFlag;
    }

    public int getRemindInterval() {
        return remindInterval;
    }

    public void setRemindInterval(int remindInterval) {
        this.remindInterval = remindInterval;
    }

    public int getRemindIntervalUnit() {
        return remindIntervalUnit;
    }

    public void setRemindIntervalUnit(int remindIntervalUnit) {
        this.remindIntervalUnit = remindIntervalUnit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public UserEventClass getUserEventClass() {
        return userEventClass;
    }

    public void setUserEventClass(UserEventClass userEventClass) {
        this.userEventClass = userEventClass;
    }
}
