package com.dabo.xunuo.base.entity;

/**
 * 联系人自定义属性
 * Created by zhangbin on 16/9/25.
 */
public class ContactProp {
    private long id;
    private String name;
    private String value;
    private long contactId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }
}
