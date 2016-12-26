package com.dabo.xunuo.app.web.controller;

import com.dabo.xunuo.app.entity.ContactClassListResponse;
import com.dabo.xunuo.app.entity.ContactDetailResponse;
import com.dabo.xunuo.app.entity.ContactListResponse;
import com.dabo.xunuo.app.service.AppContactService;
import com.dabo.xunuo.base.common.Constants;
import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.util.JsonUtils;
import com.dabo.xunuo.base.util.RequestUtils;
import com.dabo.xunuo.app.entity.ContactUpdateReq;
import com.dabo.xunuo.app.web.vo.FigurePropReq;
import com.dabo.xunuo.app.web.vo.ContactBatchCreateParam;
import com.dabo.xunuo.app.web.vo.RequestContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * 联系人相关的接口
 */
@RequestMapping("/contact")
@Controller
public class ContactController extends BaseController {

    @Autowired
    private AppContactService appContactService;

    /**
     * 获取联系人类别列表
     */
    @RequestMapping(value = "/class/list/byuser")
    @ResponseBody
    public String getTypeList() throws Exception {
        //当前登录userId
        long userId = RequestContext.getUserId();
        ContactClassListResponse response = appContactService.getContactClassByUser(userId);
        return createSuccessResponse(response);
    }

    /**
     * 修改联系人
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/figure/update")
    @ResponseBody
    public String updateContactFigure() throws Exception {
        //当前登录userId
        long userId = RequestContext.getUserId();
        String jsonParam = RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "data");
        FigurePropReq figurePropReq;
        try {
            figurePropReq = JsonUtils.toObject(jsonParam, FigurePropReq.class);
        } catch (Exception e) {
            throw new SysException("参数格式错误", Constants.ERROR_CODE_INVALID_PARAM);
        }
        appContactService.updateContactFigureProp(figurePropReq, userId);
        return createDefaultSuccessResponse();
    }

    /**
     * 批量新增联系人
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/create/batch")
    @ResponseBody
    public String batchCreateContact() throws Exception {
        //当前登录userId
        long userId = RequestContext.getUserId();
        String jsonParam = RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "data");
        ContactBatchCreateParam contactBatchCreateParam;
        try {
            contactBatchCreateParam = JsonUtils.toObject(jsonParam, ContactBatchCreateParam.class);
        } catch (Exception e) {
            throw new SysException("参数格式错误", Constants.ERROR_CODE_INVALID_PARAM);
        }

        List<ContactUpdateReq> contactList = contactBatchCreateParam.getContacts();
        if (!CollectionUtils.isEmpty(contactList)) {
            contactList.forEach(contactUpdateReq -> contactUpdateReq.setUser_id(userId));
        }
        appContactService.updateContactBatch(contactList);

        return createDefaultSuccessResponse();
    }


    /**
     * 新增修改联系人
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String updateContact() throws Exception {
        //当前登录userId
        long userId = RequestContext.getUserId();
        String jsonParam = RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "data");
        ContactUpdateReq contactUpdateReq;
        try {
            contactUpdateReq = JsonUtils.toObject(jsonParam, ContactUpdateReq.class);
        } catch (Exception e) {
            throw new SysException("参数格式错误", Constants.ERROR_CODE_INVALID_PARAM);
        }
        contactUpdateReq.setUser_id(userId);
        appContactService.updateContactBatch(Arrays.asList(contactUpdateReq));

        return createDefaultSuccessResponse();
    }

    /**
     * 获取联系人详情
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/info")
    @ResponseBody
    public String getContactInfo() throws Exception {
        long contactId = RequestUtils.getLong(RequestContext.getNotEmptyParamMap(), "contact_id");
        ContactDetailResponse response = appContactService.getContactDetail(contactId);
        return createSuccessResponse(response);
    }

    /**
     * 删除联系人
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String deleteContactInfo() throws Exception {
        long userId = RequestContext.getUserId();
        long contactId = RequestUtils.getLong(RequestContext.getNotEmptyParamMap(), "contact_id");
        appContactService.deleteContact(contactId, userId);
        return createDefaultSuccessResponse();
    }

    /**
     * 联系人列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public String contactList() throws Exception {
        //当前登录userId
        long userId = RequestContext.getUserId();
        ContactListResponse response = appContactService.getContactList(userId);
        return createSuccessResponse(response);
    }
}