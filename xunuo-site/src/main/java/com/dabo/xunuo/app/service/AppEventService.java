/**
 * Copyright (C) 2006-2016 Tuniu All rights reserved
 */
package com.dabo.xunuo.app.service;


import com.dabo.xunuo.app.entity.EventListByUserResponse;
import com.dabo.xunuo.app.entity.EventUpdateReq;
import com.dabo.xunuo.base.common.exception.SysException;

public interface AppEventService {
    /**
     * 新建或修改用户事件
     */
    void updateEvent(EventUpdateReq eventUpdateReq) throws SysException;

    /**
     * 获取用户的事件列表
     * 按照下一次发生时间倒序排序
     */
    EventListByUserResponse getEventList(long userId, int page, int limit) throws SysException;
}
