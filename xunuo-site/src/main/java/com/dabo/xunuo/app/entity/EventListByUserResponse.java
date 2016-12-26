/**
 * Copyright (C) 2006-2016 Tuniu All rights reserved
 */
package com.dabo.xunuo.app.entity;

import java.util.List;

public class EventListByUserResponse {
    private int total;
    private List<EventInfoData> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<EventInfoData> getData() {
        return data;
    }

    public void setData(List<EventInfoData> data) {
        this.data = data;
    }
}
