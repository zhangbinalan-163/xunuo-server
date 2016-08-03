package com.dabo.xunuo.event;

import com.dabo.xunuo.event.impl.UserRegEvent;
import com.dabo.xunuo.event.impl.listener.DemoListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 事件监听机制初始化配置
 * Created by zhangbin on 16/8/3.
 */
@Component
public class EventBusInitConfig {

    @Autowired
    private EventBus eventBus;

    @Autowired
    private DemoListener demoListener;

    @PostConstruct
    public void registerEventBus() {
        //注册用户注册事件
        eventBus.registerListener(UserRegEvent.class,demoListener);
    }
}
