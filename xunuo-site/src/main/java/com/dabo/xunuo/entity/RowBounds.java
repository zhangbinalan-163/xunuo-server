package com.dabo.xunuo.entity;

/**
 * 分页参数封装
 * Created by zhangbin on 16/8/27.
 */
public class RowBounds {
    private int page;
    private int limit;

    public RowBounds(int page, int limit) {
        this.page = page;
        this.limit = limit;
    }

    public int getOffSet() {
        return page==0?0:(page-1)*limit;
    }

    public int getLimit() {
        return limit;
    }
}
