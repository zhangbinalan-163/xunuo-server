/**
 * Copyright (C) 2006-2016 Tuniu All rights reserved
 */
package com.dabo.xunuo.app.web.vo;

import java.util.List;

/**
 * Date: 2016-12-17
 *
 * @author apple
 */
public class FigurePropReq {
    private int figure_type;
    private long contact_id;
    private List<PropVo> default_key_values;
    private List<PropVo> extra_key_values;

    public int getFigure_type() {
        return figure_type;
    }

    public void setFigure_type(int figure_type) {
        this.figure_type = figure_type;
    }

    public long getContact_id() {
        return contact_id;
    }

    public void setContact_id(long contact_id) {
        this.contact_id = contact_id;
    }

    public List<PropVo> getDefault_key_values() {
        return default_key_values;
    }

    public void setDefault_key_values(List<PropVo> default_key_values) {
        this.default_key_values = default_key_values;
    }

    public List<PropVo> getExtra_key_values() {
        return extra_key_values;
    }

    public void setExtra_key_values(List<PropVo> extra_key_values) {
        this.extra_key_values = extra_key_values;
    }

    public static class PropVo {
        private int prop_type;
        private String prop_name;
        private String prop_value;

        public int getProp_type() {
            return prop_type;
        }

        public void setProp_type(int prop_type) {
            this.prop_type = prop_type;
        }

        public String getProp_name() {
            return prop_name;
        }

        public void setProp_name(String prop_name) {
            this.prop_name = prop_name;
        }

        public String getProp_value() {
            return prop_value;
        }

        public void setProp_value(String prop_value) {
            this.prop_value = prop_value;
        }
    }
}
