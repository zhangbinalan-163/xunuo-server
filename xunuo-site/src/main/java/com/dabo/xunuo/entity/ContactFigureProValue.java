package com.dabo.xunuo.entity;

/**
 * 联系人形象属性
 * Created by zhangbin on 16/9/25.
 */
public class ContactFigureProValue {
    private long id;
    private int figurePropId;
    private long contactId;
    private String value;

    private ContactFigurePro contactFigurePro;

    public ContactFigurePro getContactFigurePro() {
        return contactFigurePro;
    }

    public void setContactFigurePro(ContactFigurePro contactFigurePro) {
        this.contactFigurePro = contactFigurePro;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getFigurePropId() {
        return figurePropId;
    }

    public void setFigurePropId(int figurePropId) {
        this.figurePropId = figurePropId;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
