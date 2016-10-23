package com.dabo.xunuo.dao;

import com.dabo.xunuo.entity.ContactPro;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 联系人属性
 */
public interface ContactProMapper extends BaseMapper<Integer,ContactPro>{
	/**
	 * 获取联系人的自定义属性
	 * @param contactId
	 * @return
	 */
	List<ContactPro> getProByContact(@Param("contactId")long contactId);

	/**
	 * 删除自定义属性
	 * @param contactId
	 */
	void deleteProByContact(@Param("contactId")long contactId);
}
