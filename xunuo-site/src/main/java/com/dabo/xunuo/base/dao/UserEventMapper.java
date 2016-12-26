package com.dabo.xunuo.base.dao;

import com.dabo.xunuo.base.entity.UserEvent;

import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户事件操作Mapper
 */
public interface UserEventMapper extends BaseMapper<Long, UserEvent> {
    /**
     * 设置状态
     *
     * @param contactId
     * @param state
     * @param updateTime
     */
    void setStateByContact(@Param("contactId") long contactId, @Param("state") int state, @Param("updateTime") long updateTime);

    /**
     * 获取全部事件
     */
    List<UserEvent> getAllEventByContact(@Param("contactId") long contactId, @Param("state") int state);
}

