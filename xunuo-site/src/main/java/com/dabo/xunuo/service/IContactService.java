package com.dabo.xunuo.service;

import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.entity.*;

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
	 * 获取形象详情
	 * @param figureId
	 * @return
	 * @throws SysException
	 */
	ContactFigure getContactFigure(int figureId) throws SysException;

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

	/**
	 * 获取联系人类型
	 * @param contactTypeId
	 * @return
	 * @throws SysException
	 */
	ContactType getUserContactTypeById(long contactTypeId) throws SysException;

	/**
	 * 删除用户联系人类型
	 * @param contactType
	 * @throws SysException
	 */
	void deleteContactType(ContactType contactType) throws SysException;

	/**
	 * 获取某个联系人关联的形象的属性值设置
	 * @param contactId
	 * @param figureId
	 * @return
	 */
	List<ContactFigureProValue> getFigureProWithValue(long contactId, int figureId)throws SysException;

	/**
	 * 设置联系人的形象、形象的具体属性值
	 * @param contactId
	 * @param figureId
	 * @throws SysException
	 */
	void setContactFigureProValue(long contactId,int figureId,List<ContactFigureProValue> proValueList)throws SysException;

	/**
	 * 统计类型下有多少联系人
	 * @param contactType
	 * @return
	 * @throws SysException
	 */
	long countByContactType(long contactType) throws SysException;

	/**
	 * 批量新建联系人
	 * @param contactList
	 * @throws SysException
	 */
	void batchCreateContact(List<Contact> contactList) throws SysException;

	/**
	 * 新建联系人
	 * @param contact
	 * @throws SysException
	 */
	void createContact(Contact contact) throws SysException;

	/**
	 * 修改联系人
	 * @param contact
	 * @throws SysException
	 */
	void updateContact(Contact contact) throws SysException;

	/**
	 * 获取联系人详情
	 * @param contactId
	 * @return
	 * @throws SysException
	 */
	Contact getContactById(long contactId) throws SysException;

	/**
	 * 删联系人
	 * @param contactId
	 * @return
	 * @throws SysException
	 */
	void deleteContactById(long contactId) throws SysException;
}
