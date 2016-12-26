package com.dabo.xunuo.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dabo.xunuo.base.entity.ContactNextEvent;

/**
 * 联系人下一次事件MAPPER
 */
public interface ContactNextEventMapper extends BaseMapper<Long, ContactNextEvent> {
    /**
     * 批量获取联系人下一次事件
     *
     * @param contactIds
     * @return
     */
    List<ContactNextEvent> getByContacts(@Param("contactIds") List<Long> contactIds);

    /**
     * 删除联系人的最近一个事件
     */
    void deleteByContact(@Param("contactId") long contactId);
}
