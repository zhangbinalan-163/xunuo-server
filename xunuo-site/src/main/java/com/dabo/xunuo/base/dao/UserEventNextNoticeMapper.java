package com.dabo.xunuo.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dabo.xunuo.base.entity.UserEventNextNotice;

/**
 * 用户事件下一次提醒时间MAPPER
 */
public interface UserEventNextNoticeMapper extends BaseMapper<Long, UserEventNextNotice> {

    void deleteByEventId(@Param("eventId") long eventId);

    void deleteByEventIds(@Param("eventIds") List<Long> eventIds);

    List<UserEventNextNotice> getByNoticeTime(@Param("startTime") long startTime, @Param("endTime") long endTime);
}
