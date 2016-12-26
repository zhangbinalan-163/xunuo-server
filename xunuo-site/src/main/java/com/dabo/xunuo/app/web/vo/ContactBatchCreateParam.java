package com.dabo.xunuo.app.web.vo;


import java.util.List;

import com.dabo.xunuo.app.entity.ContactUpdateReq;

/**
 * 批量新增联系人请求
 * Created by zhangbinalan on 2016/10/23.
 */
public class ContactBatchCreateParam {
    private List<ContactUpdateReq> contacts;

    public List<ContactUpdateReq> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactUpdateReq> contacts) {
        this.contacts = contacts;
    }

}
