/**
 * Copyright (C) 2006-2016 Tuniu All rights reserved
 */
package com.dabo.xunuo.app.service;

import java.util.List;

import com.dabo.xunuo.app.entity.ContactClassListResponse;
import com.dabo.xunuo.app.entity.ContactDetailResponse;
import com.dabo.xunuo.app.entity.ContactListResponse;
import com.dabo.xunuo.app.entity.ContactUpdateReq;
import com.dabo.xunuo.app.web.vo.FigurePropReq;
import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.entity.Contact;

public interface AppContactService {

    /**
     * 获取用户的联系人类别列表
     *
     * @param userId
     * @return
     * @throws SysException
     */
    ContactClassListResponse getContactClassByUser(long userId) throws SysException;

    /**
     * 获取事件详情
     *
     * @param contactId
     * @return
     * @throws SysException
     */
    ContactDetailResponse getContactDetail(long contactId) throws SysException;

    /**
     * 查询用户的联系人列表
     *
     * @param userId
     * @return
     * @throws SysException
     */
    ContactListResponse getContactList(long userId) throws SysException;

    /**
     * 删除联系人
     */
    void deleteContact(long contactId, long currentUser) throws SysException;

    /**
     * 新建或修改联系人
     */
    void updateContactBatch(List<ContactUpdateReq> contactUpdateReqList) throws SysException;

    /**
     * 修改形象属性信息
     */
    void updateContactFigureProp(FigurePropReq figurePropReq,long currentUser) throws SysException;
}
