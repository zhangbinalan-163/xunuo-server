package com.dabo.xunuo.event;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 观察者管理器
 * 考虑到很少会动态的修改观察者关系,不做同步控制
 */
public class ListenerManager {
    private HashMap<Class<? extends Event>,List<EventListener>> event2ListenerMap;

    public ListenerManager() {
        event2ListenerMap=new HashMap();
    }

    public void registerListener(Class<? extends Event> eventType,EventListener eventListener){
        List<EventListener> nowListenerList=event2ListenerMap.get(eventType);
        if(CollectionUtils.isEmpty(nowListenerList)){
            nowListenerList=new ArrayList<>();
        }
        nowListenerList.add(eventListener);
        event2ListenerMap.put(eventType,nowListenerList);
    }

    public void unRegisterListener(Class<? extends Event> eventType,EventListener eventListener){
        List<EventListener> nowListenerList=event2ListenerMap.get(eventType);
        if(!CollectionUtils.isEmpty(nowListenerList)){
            nowListenerList=nowListenerList.stream().filter(watcherItem-> watcherItem!=eventListener).collect(toList());
            event2ListenerMap.put(eventType,nowListenerList);
        }
    }

    /**
     * 获取某个事件的观察者
     * @param event
     * @return
     */
    public List<EventListener> getListeners(Event event){
        Class<? extends Event> classType = event.getClass();
        return event2ListenerMap.get(classType);
    }
}
