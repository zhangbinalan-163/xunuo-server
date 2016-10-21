package com.dabo.xunuo.entity;

/**
 * 联系人形象属性
 * Created by zhangbin on 16/9/25.
 */
public class ContactFigurePro {
    private int id;
    private String name;
    private int figureId;
    private String propUnit;

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

    public int getFigureId() {
        return figureId;
    }

    public void setFigureId(int figureId) {
        this.figureId = figureId;
    }

    public String getPropUnit() {
        return propUnit;
    }

    public void setPropUnit(String propUnit) {
        this.propUnit = propUnit;
    }
}
