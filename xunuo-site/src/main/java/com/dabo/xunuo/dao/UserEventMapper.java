package com.dabo.xunuo.dao;

import com.dabo.xunuo.entity.RowBounds;
import com.dabo.xunuo.entity.UserEvent;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户事件操作Mapper
 */
public interface UserEventMapper extends BaseMapper<Long,UserEvent>{

    /**
     * 分页获取用户的事件
     * @param userId
     * @param eventTime 返回的事件的时间必须大于此时间
     * @param rowBounds
     * @return
     */
    List<UserEvent> getEventByUser(@Param("userId") long userId,
                                   @Param("eventTime") long eventTime,
                                   @Param("field") String field,
                                   @Param("direction") String direction,
                                   @Param("rowBounds") RowBounds rowBounds);

    /**
     * 计算总数获取用户的事件
     * @param userId
     * @param eventTime 返回的事件的时间必须大于此时间
     * @return
     */
    int countEventByUser(@Param("userId") long userId, @Param("eventTime") long eventTime);


    /**
     * 统计数量
     * @param contactType
     * @return
     */
    long countByType(@Param("typeId") long contactType);

    /**
     * 删除联系人关联的事件
     * @param contactId
     */
    void deleteByContactId(@Param("contactId") long contactId);

    /**
     * 获取联系人的事件
     * @param contactId
     * @param eventTime
     * @param field
     * @param direction
     * @param rowBounds
     * @return
     */
    List<UserEvent> getEventByContact(@Param("contactId") long contactId,
                                   @Param("eventTime") long eventTime,
                                   @Param("field") String field,
                                   @Param("direction") String direction,
                                   @Param("rowBounds") RowBounds rowBounds);

}

