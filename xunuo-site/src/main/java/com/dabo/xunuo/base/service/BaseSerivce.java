package com.dabo.xunuo.base.service;

import com.dabo.xunuo.base.event.EventBus;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基础业务
 * Created by zhangbin on 16/8/3.
 */
public class BaseSerivce {

    @Autowired
    protected EventBus eventBus;


}
