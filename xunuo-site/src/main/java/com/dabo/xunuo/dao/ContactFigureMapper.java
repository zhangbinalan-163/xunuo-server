package com.dabo.xunuo.dao;

import com.dabo.xunuo.entity.ContactFigure;

import java.util.List;

/**
 * 联系人形象
 */
public interface ContactFigureMapper extends BaseMapper<Integer,ContactFigure>{
	/**
	 * 获得全部的联系人形象
	 * @return
	 */
	List<ContactFigure> getAllFigure();
}
