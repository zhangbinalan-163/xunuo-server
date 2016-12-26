package com.dabo.xunuo.base.event;

/**
 * 事件观察者<br/>
 * Created by zhangbin2 on 16/6/23.
 */
public interface EventListener {
    /**
     * 处理事件
     * @param event
     * @throws Exception
     */
    void onEvent(Event event) throws Exception;
}
