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
     * 删除用户事件类型
     * @param userEventType
     * @throws SysException
     */
    void deleteUserEventType(UserEventType userEventType) throws SysException;

    /**
     * 创建用户事件
     * @param userEvent
     * @throws SysException
     */
    void createUserEvent(UserEvent userEvent) throws SysException;

    /**
     * 获得事件类型
     * @param userEventTypeId
     * @return
     * @throws SysException
     */
    UserEventType getUserEventTypeById(long userEventTypeId) throws SysException;

    /**
     * 创建用户事件
     * @param userEvent
     * @throws SysException
     */
    void updateUserEvent(UserEvent userEvent) throws SysException;

    /**
     * 获取用户事件
     * @param eventId
     * @return
     * @throws SysException
     */
    UserEvent getUserEventById(long eventId) throws SysException;


    /**
     * 统计类型下有多少事件
     * @param eventTypeId
     * @return
     * @throws SysException
     */
    long countByEventType(long eventTypeId) throws SysException;

    /**
     * 删除联系人的事件
     *
     * @param contactId
     * @throws SysException
     */
    void deleteUserEventByContactId(long contactId) throws SysException;

    /**
     * 获取联系人最近要发生的事
     * @param contactId
     * @return
     * @throws SysException
     */
    UserEvent getNeariestEventByContact(long contactId) throws SysException;
}
