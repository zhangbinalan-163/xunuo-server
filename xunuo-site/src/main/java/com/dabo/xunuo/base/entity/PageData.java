package com.dabo.xunuo.base.entity;

import java.util.List;

/**
 * 分页数据
 * Created by zhangbin on 16/8/28.
 */
public class PageData<T> {

    private int total;
    private List<T> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
