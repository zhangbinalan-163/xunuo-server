package com.dabo.xunuo.base.dao;

import com.dabo.xunuo.base.entity.RowBounds;
import com.dabo.xunuo.base.entity.ContactType;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContactTypeMapper extends BaseMapper<Integer,ContactType>{

	/**
	 * 计数
	 * @param userId
	 * @param sourceType
	 * @return
	 */
	int countByUser(@Param("userId") long userId, @Param("sourceType") int sourceType);

	/**
	 * 获取指定用户+指定来源的联系人类型
	 * @param userId
	 * @param sourceType
	 * @return
	 */
	List<ContactType> getContactTypeByUser(@Param("userId") long userId,
										   @Param("sourceType") int sourceType,
										   @Param("field") String field,
										   @Param("direction") String direction,
										   @Param("rowBounds") RowBounds rowBounds);

	/**
	 * 获取用户+指定来源的类型的最大sort_index
	 * @param userId
	 * @param sourceType
	 * @return
	 */
	int getMaxSortIndex(@Param("userId") long userId,
						@Param("sourceType") int sourceType);
}
