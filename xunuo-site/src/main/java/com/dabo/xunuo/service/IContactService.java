package com.dabo.xunuo.service;

import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.entity.ContactFigure;
import com.dabo.xunuo.entity.ContactFigurePro;
import com.dabo.xunuo.entity.ContactType;

import java.util.List;

/**
 * 联系人业务接口
 */
public interface IContactService {
	/**
	 * 新增联系人形象
	 * @param contactFigure
	 * @throws SysException
	 */
	void createContactFigure(ContactFigure contactFigure) throws SysException;

	/**
	 * 获取全部联系人形象
	 * 按照添加时间升序
	 * @return
	 * @throws SysException
	 */
	List<ContactFigure> getAllContactFigure() throws SysException;

	/**
	 * 获得形象的全部属性
	 * 按照添加时间升序
	 * @param figureId
	 * @return
	 * @throws SysException
	 */
	List<ContactFigurePro> getFigurePro(int figureId) throws SysException;

	/**
	 * 删除联系人形象
	 * @param contactFigure
	 * @throws SysException
	 */
	void deleteContactFigure(ContactFigure contactFigure) throws SysException;

	/**
	 * 根据用户获取用户联系人类型
	 * 系统定义的类型在前,用户自定义类型在后面
	 * @param userId
	 * @return
	 * @throws SysException
	 */
	List<ContactType> getUserContactType(long userId) throws SysException;

	/**
	 * 创建联系人类型
	 * @throws SysException
	 */
	void createContactType(ContactType contactType) throws SysException;
}
