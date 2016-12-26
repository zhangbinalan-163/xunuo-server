/**
 * Copyright (C) 2006-2016 Tuniu All rights reserved
 */
package com.dabo.xunuo.app.entity;


public class EventInfoData {
    private long event_id;
    private String name;
    private int days_remain;
    private long event_time;
    private int chinese_calendar_flag;
    private int remind_interval;
    private int remind_interval_type;
    private String remark;
    private EventClassInfo event_class;
    private ContactInfoData contactInfo;

    public long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(long event_id) {
        this.event_id = event_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDays_remain() {
        return days_remain;
    }

    public void setDays_remain(int days_remain) {
        this.days_remain = days_remain;
    }

    public long getEvent_time() {
        return event_time;
    }

    public void setEvent_time(long event_time) {
        this.event_time = event_time;
    }

    public int getChinese_calendar_flag() {
        return chinese_calendar_flag;
    }

    public void setChinese_calendar_flag(int chinese_calendar_flag) {
        this.chinese_calendar_flag = chinese_calendar_flag;
    }

    public int getRemind_interval() {
        return remind_interval;
    }

    public void setRemind_interval(int remind_interval) {
        this.remind_interval = remind_interval;
    }

    public int getRemind_interval_type() {
        return remind_interval_type;
    }

    public void setRemind_interval_type(int remind_interval_type) {
        this.remind_interval_type = remind_interval_type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public EventClassInfo getEvent_class() {
        return event_class;
    }

    public void setEvent_class(EventClassInfo event_class) {
        this.event_class = event_class;
    }

    public ContactInfoData getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfoData contactInfo) {
        this.contactInfo = contactInfo;
    }

    public static class EventClassInfo {
        private int event_class_id;
        private int source_type;
        private String name;
        private int bigday_flag;

        public int getEvent_class_id() {
            return event_class_id;
        }

        public void setEvent_class_id(int event_class_id) {
            this.event_class_id = event_class_id;
        }

        public int getSource_type() {
            return source_type;
        }

        public void setSource_type(int source_type) {
            this.source_type = source_type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBigday_flag() {
            return bigday_flag;
        }

        public void setBigday_flag(int bigday_flag) {
            this.bigday_flag = bigday_flag;
        }
    }

    public static class ContactInfoData {
        private long contact_id;
        private String name;
        private String head_url;

        public long getContact_id() {
            return contact_id;
        }

        public void setContact_id(long contact_id) {
            this.contact_id = contact_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHead_url() {
            return head_url;
        }

        public void setHead_url(String head_url) {
            this.head_url = head_url;
        }
    }
}