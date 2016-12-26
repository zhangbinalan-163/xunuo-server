package com.dabo.xunuo.base.entity;

import java.util.List;

/**
 * 联系人形象
 * Created by zhangbin on 16/9/25.
 */
public class ContactFigurePropValues {
    private List<ContactFigureProp> figurePropValueList;
    private List<ContactFigureProp> selfPropValueList;

    public List<ContactFigureProp> getFigurePropValueList() {
        return figurePropValueList;
    }

    public void setFigurePropValueList(List<ContactFigureProp> figurePropValueList) {
        this.figurePropValueList = figurePropValueList;
    }

    public List<ContactFigureProp> getSelfPropValueList() {
        return selfPropValueList;
    }

    public void setSelfPropValueList(List<ContactFigureProp> selfPropValueList) {
        this.selfPropValueList = selfPropValueList;
    }
}
