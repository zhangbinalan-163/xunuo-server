package com.dabo.xunuo.dao;

import com.dabo.xunuo.entity.ContactFigurePro;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 联系人形象属性
 */
public interface ContactFigureProMapper extends BaseMapper<Integer,ContactFigurePro>{
	/**
     * 获得某个联系人形象的属性
	 * @return
     */
	List<ContactFigurePro> getProByFigure(@Param("figureId")int figureId);

	/**
	 * 删除形象的属性
	 * @param figureId
	 */
	void deleteByFigure(@Param("figureId")int figureId);
}
