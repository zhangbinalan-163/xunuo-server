package com.dabo.xunuo.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.dabo.xunuo.entity.ContactType;
public interface ContactTypeMapper extends BaseMapper<Long,ContactType>{
	
	List<ContactType>  getContactTypeListbyuser(@Param("userId")long userId,@Param("orderkey")String orderkey);
}
