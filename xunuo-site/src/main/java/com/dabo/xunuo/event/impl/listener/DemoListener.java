package com.dabo.xunuo.event.impl.listener;

import com.dabo.xunuo.entity.User;
import com.dabo.xunuo.event.Event;
import com.dabo.xunuo.event.EventListener;
import com.dabo.xunuo.event.impl.UserRegEvent;
import com.dabo.xunuo.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 一个事件监听器实例
 * Created by zhangbin on 16/8/3.
 */
@Component
public class DemoListener implements EventListener{
    private static Logger LOG= LoggerFactory.getLogger(DemoListener.class);

    @Override
    public void onEvent(Event event) throws Exception {
        //用户注册事件
        if(event instanceof UserRegEvent){
            User user=((UserRegEvent) event).getSource();
            processUserReg(user);
        }
    }

    private void processUserReg(User user) {
        //这是个例子,只是打印日志
        LOG.info("user register,userInfo={}", JsonUtils.fromObject(user));
    }
}
