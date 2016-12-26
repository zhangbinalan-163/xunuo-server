/**
 * Copyright (C) 2006-2016 Tuniu All rights reserved
 */
package com.dabo.xunuo.app.entity;

public class EventData {
    private long event_id;
    private String name;
    private int days_remain;

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
}
