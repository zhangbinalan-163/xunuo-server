package com.dabo.xunuo.base.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 事件总线
 */
@Component
public class EventBus {
    private static Logger LOGGER= LoggerFactory.getLogger(EventBus.class);

    private ListenerManager listenerManager;

    public EventBus() {
        listenerManager =new ListenerManager();
    }

    /**
     * 触发事件
     * @param event
     */
    public void post(Event event){
        List<EventListener> listenerList= listenerManager.getListeners(event);
        //获取这个事件的所有监听者
        if(!CollectionUtils.isEmpty(listenerList)){
            //默认按照顺序全部触发事件
            listenerList.forEach(eventListener->{
                try {
                    eventListener.onEvent(event);
                } catch (Exception e) {
                    LOGGER.error("process event fail,eventListener={},eventType={}",
                            eventListener.getClass().getName(),
                            event.getClass().getName(),
                            e);
                }
            });
        }
    }

    /**
     * 注册指定事件的监听器
     * @param eventType
     * @param eventListener
     */
    public void registerListener(Class<? extends Event> eventType,EventListener eventListener){
        listenerManager.registerListener(eventType, eventListener);
    }
    /**
     * 注册指定事件的监听器
     * @param eventType
     * @param eventListener
     */
    public void unRegisterListener(Class<? extends Event> eventType,EventListener eventListener){
        listenerManager.unRegisterListener(eventType, eventListener);
    }

}
