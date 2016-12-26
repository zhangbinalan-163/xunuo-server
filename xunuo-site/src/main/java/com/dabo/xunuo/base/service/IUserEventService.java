package com.dabo.xunuo.base.service;

import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.entity.ContactNextEvent;
import com.dabo.xunuo.base.entity.PageData;
import com.dabo.xunuo.base.entity.UserEvent;
import com.dabo.xunuo.base.entity.UserEventClass;
import com.dabo.xunuo.base.entity.UserEventNextTrigger;

import java.util.List;
import java.util.Map;

/**
 * 用户事件业务接口
 * Created by zhangbin on 16/8/28.
 */
public interface IUserEventService {

    /**
     * 根据用户获取用户事件类型
     * 系统定义的类型在前,用户自定义类型在后面
     *
     * @param userId
     * @return
     * @throws SysException
     */
    List<UserEventClass> getUserEventClass(long userId) throws SysException;

    /**
     * 创建用户自定义事件类型
     *
     * @param userEventType
     * @throws SysException
     */
    void createUserEventClass(UserEventClass userEventType) throws SysException;

    /**
     * 创建用户自定义事件类型
     *
     * @param userId
     * @param eventClassName
     * @param eventTime
     * @throws SysException
     */
    UserEventClass createUserEventClass(long userId, String eventClassName, long eventTime) throws SysException;

    /**
     * 创建用户事件
     *
     * @param userEvent
     * @throws SysException
     */
    void createUserEvent(UserEvent userEvent) throws SysException;

    /**
     * 创建用户事件
     *
     * @param userEvent
     * @throws SysException
     */
    void updateUserEvent(UserEvent userEvent, UserEvent oldUserEvent) throws SysException;

    /**
     * 获取用户事件
     *
     * @param eventId
     * @return
     * @throws SysException
     */
    UserEvent getUserEventById(long eventId) throws SysException;

    /**
     * 删除联系人的事件
     *
     * @param contactId
     * @throws SysException
     */
    void deleteUserEventByContactId(long contactId) throws SysException;

    /**
     * delete
     *
     * @param userEvent
     */
    void deleteUserEvent(UserEvent userEvent);

    /**
     * 获取事件信息
     */
    Map<Long, UserEvent> getUserEventByIds(List<Long> eventIds);


    /**
     * 获取联系人的下一个要触发的事件
     */
    Map<Long, ContactNextEvent> getContactNextEvent(List<Long> contactIds);

    /**
     * 计算事件的最近一个发生时间
     */
    ContactNextEvent calculateNextEventByEvent(UserEvent userEvent);

    /**
     * 计算联系人的最近一个事件
     */
    ContactNextEvent getNextEventByContact(long contactId);

    /**
     * 获取列表,按照事件倒序
     */
    PageData<UserEventNextTrigger> getEventNextTriggerList(long userId, int page, int limit);

    /**
     */
    UserEventClass getEventClass(int eventClassId);
}
