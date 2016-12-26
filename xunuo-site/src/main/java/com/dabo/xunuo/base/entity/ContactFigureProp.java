package com.dabo.xunuo.base.entity;

/**
 * 联系人形象
 * Created by zhangbin on 16/9/25.
 */
public class ContactFigureProp {
    public static final int SYSTEM_DEDINE = 0;
    public static final int USER_DEDINE = 1;

    private long id;
    private String prop;
    private String value;
    private long contactId;
    private int propType;

    public int getPropType() {
        return propType;
    }

    public void setPropType(int propType) {
        this.propType = propType;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactFigureProp that = (ContactFigureProp) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
