package com.dabo.xunuo.base.entity;

/**
 * 联系人类型
 */
public class ContactType {
    public static final int SOURCE_SYSTEM = 0;
    public static final int SOURCE_USER = 1;
    public static ContactType DEFAULT_TYPE;

    private int id;
    private String name;
    private int sourceType;
    private long userId;
    private long createTime;
    private long updateTime;
    private int sortIndex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactType that = (ContactType) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    public static ContactType defaultType() {
        if (DEFAULT_TYPE == null) {
            DEFAULT_TYPE = new ContactType();
            DEFAULT_TYPE.setId(0);
            DEFAULT_TYPE.setName("默认");
        }
        return DEFAULT_TYPE;
    }
}
