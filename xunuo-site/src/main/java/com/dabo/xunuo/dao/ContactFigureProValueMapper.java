package com.dabo.xunuo.dao;

import com.dabo.xunuo.entity.ContactFigureProValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 联系人形象属性值关联表
 */
public interface ContactFigureProValueMapper extends BaseMapper<Long,ContactFigureProValue>{
	/**
     * 获得某个联系人形象的属性值关联关系
	 * @return
     */
	List<ContactFigureProValue> getProValueByContact(@Param("contactId") long contactId,@Param("proIdList")List<Integer> proIdList);

	/**
	 * 删除关联关系
	 * @param contactId
	 * @param proIdList
	 */
	void deleteProValueByContact(@Param("contactId") long contactId,@Param("proIdList")List<Integer> proIdList);
}
