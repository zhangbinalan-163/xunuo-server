package com.dabo.xunuo.base.event;

/**
 * 事件
 * @param <T>
 */
public class Event<T> {
    private T source;//事件产生源
    private long createTime;//事件产生时间

    public Event(T source) {
        this.source = source;
        this.createTime=System.currentTimeMillis();
    }

    public T getSource() {
        return source;
    }

    public long getCreateTime() {
        return createTime;
    }
}
