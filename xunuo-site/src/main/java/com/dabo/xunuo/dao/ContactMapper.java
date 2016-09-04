package com.dabo.xunuo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dabo.xunuo.entity.Contact;
import com.dabo.xunuo.entity.RowBounds;

public interface ContactMapper extends BaseMapper<Long,Contact>{
	List<Contact>  getContactList(@Param("userId")long userId,@Param("contacttypeid")int contacttypeid,@Param("orderkey")String orderkey,@Param("rowBounds")RowBounds rowBounds);

}
