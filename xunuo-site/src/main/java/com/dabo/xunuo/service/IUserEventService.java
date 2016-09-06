package com.dabo.xunuo.service;

import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.entity.PageData;
import com.dabo.xunuo.entity.UserEvent;
import com.dabo.xunuo.entity.UserEventType;

import java.util.List;

/**
 * 用户事件业务接口
 * Created by zhangbin on 16/8/28.
 */
public interface IUserEventService {
    /**
     * 分页获取用户的事件信息
     * 事件的发生时间必须大于当前时间
     * 按照 事件的发生时间到当前时间的间隔大小 正序排序
     * @param userId
     * @param page
     * @param limit
     * @return
     * @throws SysException
     */
    PageData<UserEvent> getUserEvent(long userId, int page, int limit) throws SysException;

    /**
     * 根据用户获取用户事件类型
     * 系统定义的类型在前,用户自定义类型在后面
     * @param userId
     * @return
     * @throws SysException
     */
    List<UserEventType> getUserEventType(long userId) throws SysException;

    /**
     * 创建用户自定义事件类型
     * @param userEventType
     * @throws SysException
     */
    void createUserEventType(UserEventType userEventType) throws SysException;

    /**
     * 创建用户事件
     * @param userEvent
     * @throws SysException
     */
    void createUserEvent(UserEvent userEvent) throws SysException;
}
