package com.dabo.xunuo.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dabo.xunuo.base.entity.ContactNextEvent;
import com.dabo.xunuo.base.entity.RowBounds;
import com.dabo.xunuo.base.entity.UserEvent;
import com.dabo.xunuo.base.entity.UserEventNextTrigger;

/**
 * 用户事件下一次触发时间MAPPER
 */
public interface UserEventNextTriggerMapper extends BaseMapper<Long, UserEventNextTrigger> {
    /**
     * 删除联系人的记录
     */
    void deleteByEventId(@Param("eventId") long eventId);

    /**
     * 删除联系人的记录
     */
    void deleteByEventIds(@Param("eventIds") List<Long> eventIds);

    /**
     * 分页获取
     * 按照发生时间正序
     */
    List<UserEventNextTrigger> getByUser(@Param("userId") long userId, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 计数
     */
    int countByUser(@Param("userId") long userId);
}
