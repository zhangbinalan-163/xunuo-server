package com.dabo.xunuo.base.dao;

import com.dabo.xunuo.base.entity.ContactFigureProp;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 联系人形象
 */
public interface ContactFigurePropMapper extends BaseMapper<Long,ContactFigureProp>{

	List<ContactFigureProp> getByContacts(@Param("contactIds")List<Long> contactIds);

	void deleteByContact(@Param("contactId")long contactId);
}
