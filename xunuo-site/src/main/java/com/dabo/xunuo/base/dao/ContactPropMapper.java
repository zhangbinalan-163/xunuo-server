package com.dabo.xunuo.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dabo.xunuo.base.entity.ContactProp;

/**
 * 联系人形象
 */
public interface ContactPropMapper extends BaseMapper<Long,ContactProp>{

	List<ContactProp> getByContacts(@Param("contactIds") List<Long> contactIds);

	void deleteByContact(@Param("contactId") long contactId);
}
