/**
 * Copyright (C) 2006-2016 Tuniu All rights reserved
 */
package com.dabo.xunuo.app.entity;

import java.util.Date;
import java.util.List;

import com.dabo.xunuo.base.entity.ContactProp;

public class ContactUpdateReq {
    private long user_id;
    private long contact_id;
    private String head_url;
    private int figure_id;
    private String name;
    private int contact_class_id;
    private String contact_class_name;
    private Date birth;
    private String phone;
    private String remark;
    private List<ContactProp> self_prop;

    public List<ContactProp> getSelf_prop() {
        return self_prop;
    }

    public void setSelf_prop(List<ContactProp> self_prop) {
        this.self_prop = self_prop;
    }

    public long getContact_id() {
        return contact_id;
    }

    public void setContact_id(long contact_id) {
        this.contact_id = contact_id;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public int getFigure_id() {
        return figure_id;
    }

    public void setFigure_id(int figure_id) {
        this.figure_id = figure_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContact_class_id() {
        return contact_class_id;
    }

    public void setContact_class_id(int contact_class_id) {
        this.contact_class_id = contact_class_id;
    }

    public String getContact_class_name() {
        return contact_class_name;
    }

    public void setContact_class_name(String contact_class_name) {
        this.contact_class_name = contact_class_name;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
