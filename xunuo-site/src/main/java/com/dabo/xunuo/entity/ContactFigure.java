package com.dabo.xunuo.entity;

import java.util.List;

/**
 * 联系人形象
 * Created by zhangbin on 16/9/25.
 */
public class ContactFigure {
    private int id;
    private String name;
    private String headUrl;
    private int sortIndex;
    private List<ContactFigurePro> proList;

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

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

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public List<ContactFigurePro> getProList() {
        return proList;
    }

    public void setProList(List<ContactFigurePro> proList) {
        this.proList = proList;
    }
}
