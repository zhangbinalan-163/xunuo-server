package com.dabo.xunuo.app.web.vo;

import java.util.List;

/**
 * 设置联系人属性的请求参数
 * Created by zhangbinalan on 2016/10/23.
 */
public class FigureProSetParam {
    private long contact_id;
    private int figure_id;
    private List<Prop> props;

    public long getContact_id() {
        return contact_id;
    }

    public void setContact_id(long contact_id) {
        this.contact_id = contact_id;
    }

    public int getFigure_id() {
        return figure_id;
    }

    public void setFigure_id(int figure_id) {
        this.figure_id = figure_id;
    }

    public List<Prop> getProps() {
        return props;
    }

    public void setProps(List<Prop> props) {
        this.props = props;
    }

    public static class Prop{
        private int prop_id;
        private String value;

        public int getProp_id() {
            return prop_id;
        }

        public void setProp_id(int prop_id) {
            this.prop_id = prop_id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
