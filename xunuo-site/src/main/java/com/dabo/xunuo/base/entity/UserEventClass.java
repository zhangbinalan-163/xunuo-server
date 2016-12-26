package com.dabo.xunuo.base.entity;

/**
 * 用户事件实体
 * Created by zhangbin on 16/8/28.
 */
public class UserEventClass {
    public static final int TYPE_ONLY_ONCE = 0;
    public static final int TYPE_EVERY_MONTH = 2;
    public static final int TYPE_EVERY_YEAR = 1;

    public static final int NOTICE_EVERY_100DAY = 1;
    public static final int NOTICE_EVERY_100DAY_NOT = 0;

    public static final int SOURCE_SYSTEM = 0;
    public static final int SOURCE_USER = 1;

    private int id;
    private String name;
    private int sourceType;
    private int classType;
    private long userId;
    private int sortIndex;
    private int bigDayFlag;
    private long createTime;
    private long updateTime;

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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
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

    public int getBigDayFlag() {
        return bigDayFlag;
    }

    public void setBigDayFlag(int bigDayFlag) {
        this.bigDayFlag = bigDayFlag;
    }

    public int getClassType() {
        return classType;
    }

    public void setClassType(int classType) {
        this.classType = classType;
    }
}

