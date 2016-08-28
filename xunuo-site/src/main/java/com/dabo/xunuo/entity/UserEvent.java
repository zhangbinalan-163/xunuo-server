package com.dabo.xunuo.entity;

/**
 * 用户事件实体
 * Created by zhangbin on 16/8/28.
 */
public class UserEvent {
    private long id;
    private long userId;//事件的所有者
    private String name;
    private long contactId;//事件关联的联系人
    private long createTime;
    private long updateTime;
    private long eventTime;
    private long eventType;
    private int remindInterval;
    private int remindIntervalUnit;
    private String remark;

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

    public long getEventType() {
        return eventType;
    }

    public void setEventType(long eventType) {
        this.eventType = eventType;
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
}
