package com.dabo.xunuo.service;

import com.dabo.xunuo.event.EventBus;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基础业务
 * Created by zhangbin on 16/8/3.
 */
public class BaseSerivce {

    @Autowired
    protected EventBus eventBus;


}
