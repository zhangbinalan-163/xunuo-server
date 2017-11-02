package com.dabo.xunuo.base.dao;

import java.util.List;

import com.dabo.xunuo.base.entity.Contact;

import org.apache.ibatis.annotations.Param;

/**
 * 联系人表MAPPER
 */
public interface ContactMapper extends BaseMapper<Long, Contact> {
    /**
     * 获取用户全部联系人
     *
     * @param userId
     * @return
     */
    List<Contact> getAllByUser(@Param("userId") long userId, @Param("state") int state);

    /**
     * 设置状态
     *
     * @param contactId
     * @param state
     * @param updateTime
     */
    void setState(@Param("contactId") long contactId, @Param("state") int state, @Param("updateTime") long updateTime);

    /**
     * 设置形象ID
     * @param contactId
     * @param figureId
     * @param updateTime
     */
    void setFigureId(@Param("contactId") long contactId, @Param("figureId") int figureId, @Param("updateTime") long updateTime);


    List<Contact> getAllByState(@Param("state") int state);
}
