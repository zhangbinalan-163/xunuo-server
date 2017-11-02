package com.dabo.xunuo.base.dao;

import com.dabo.xunuo.base.entity.UserEvent;

import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户事件操作Mapper
 */
public interface UserEventMapper extends BaseMapper<Long, UserEvent> {

    void setNextEventTime(@Param("eventId") long eventId, @Param("nextTime") long nextTime, @Param("updateTime") long updateTime);

    void setState(@Param("eventIds") List<Long> eventIds, @Param("state") int state, @Param("updateTime") long updateTime);

    /**
     * 获取全部事件
     */
    List<UserEvent> getAllEventByContact(@Param("contactId") long contactId, @Param("state") int state);

    /**
     * 获取全部事件
     */
    List<UserEvent> getAllEventByUser(@Param("userId") long userId, @Param("state") int state);

    /**
     * 获取全部事件
     */
    List<UserEvent> getAllEvent(@Param("state") int state);
}

