package com.dabo.xunuo.base.service;

import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.entity.Contact;
import com.dabo.xunuo.base.entity.ContactFigurePropValues;
import com.dabo.xunuo.base.entity.ContactProp;
import com.dabo.xunuo.base.entity.ContactType;

import java.util.List;
import java.util.Map;

/**
 * 联系人业务接口
 */
public interface IContactService {
    /**
     * 获取用户全部联系人
     *
     * @param userId
     * @return
     * @throws SysException
     */
    List<Contact> getContactByUser(long userId) throws SysException;

    /**
     * 根据用户获取用户联系人类型
     * 系统定义的类型在前,用户自定义类型在后面
     *
     * @param userId
     * @return
     * @throws SysException
     */
    List<ContactType> getUserContactType(long userId) throws SysException;

    /**
     * 创建联系人类型
     *
     * @throws SysException
     */
    void createContactType(ContactType contactType) throws SysException;

    /**
     * 新建联系人
     *
     * @param contact
     * @throws SysException
     */
    void createContact(Contact contact) throws SysException;

    /**
     * 修改联系人
     *
     * @param contact
     * @throws SysException
     */
    void updateContact(Contact contact) throws SysException;

    /**
     * 获取联系人详情
     *
     * @param contactId
     * @return
     * @throws SysException
     */
    Contact getContactById(long contactId) throws SysException;

    /**
     * 删联系人
     * 级联删除自定义属性、形象属性、关联事件
     */
    void deleteContactById(long contactId) throws SysException;

    /**
     * 获取联系人详情
     */
    List<Contact> getContactByIds(List<Long> contactIds) throws SysException;

    /**
     * 获取联系人形象的属性设置
     */
    Map<Long, ContactFigurePropValues> getContactFigurePropValue(List<Long> contactIds);

    /**
     * 联系人自定义属性
     */
    Map<Long, List<ContactProp>> getContactProp(List<Long> contactIds);

    /**
     * 联系人类型信息
     */
    Map<Integer, ContactType> getContactType(List<Integer> contactTypeIds);

    /**
     * 设置联系人自定义属性
     */
    void setContactProp(long contactId, List<ContactProp> contactPropList);

    /**
     * 设置联系人形象属性信息
     */
    void setContactFigurePropValue(long contactId, ContactFigurePropValues propValues);

    /**
     * 获取联系人详情
     */
    Map<Long, Contact> getContactMapByIds(List<Long> contactIds) throws SysException;
}
