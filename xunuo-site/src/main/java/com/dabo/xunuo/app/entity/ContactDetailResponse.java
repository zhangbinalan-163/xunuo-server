/**
 * Copyright (C) 2006-2016 Tuniu All rights reserved
 */
package com.dabo.xunuo.app.entity;

import java.util.List;

/**
 * 联系人详情结果集
 * Date: 2016-12-18
 *
 * @author zhangbinalan
 */
public class ContactDetailResponse {
    private long contact_id;
    private String head_url;
    private String birth;
    private String phone;
    private String name;
    private String remark;
    private int contact_class_id;
    private String contact_class_name;
    private List<SelfProp> self_prop;
    private FigureWithValue figure;
    private EventData event;

    public long getContact_id() {
        return contact_id;
    }

    public void setContact_id(long contact_id) {
        this.contact_id = contact_id;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getContact_class_id() {
        return contact_class_id;
    }

    public void setContact_class_id(int contact_class_id) {
        this.contact_class_id = contact_class_id;
    }

    public String getContact_class_name() {
        return contact_class_name;
    }

    public void setContact_class_name(String contact_class_name) {
        this.contact_class_name = contact_class_name;
    }

    public List<SelfProp> getSelf_prop() {
        return self_prop;
    }

    public void setSelf_prop(List<SelfProp> self_prop) {
        this.self_prop = self_prop;
    }

    public FigureWithValue getFigure() {
        return figure;
    }

    public void setFigure(FigureWithValue figure) {
        this.figure = figure;
    }

    public EventData getEvent() {
        return event;
    }

    public void setEvent(EventData event) {
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class SelfProp {
        private String name;
        private String value;

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
    }

    public static class FigureWithValue {
        private int figure_id;
        private List<FigureProp> default_key_values;
        private List<FigureProp> extra_key_values;

        public int getFigure_id() {
            return figure_id;
        }

        public void setFigure_id(int figure_id) {
            this.figure_id = figure_id;
        }

        public List<FigureProp> getDefault_key_values() {
            return default_key_values;
        }

        public void setDefault_key_values(List<FigureProp> default_key_values) {
            this.default_key_values = default_key_values;
        }

        public List<FigureProp> getExtra_key_values() {
            return extra_key_values;
        }

        public void setExtra_key_values(List<FigureProp> extra_key_values) {
            this.extra_key_values = extra_key_values;
        }
    }

    public static class FigureProp {
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
