/**
 * Copyright (C) 2006-2016 Tuniu All rights reserved
 */
package com.dabo.xunuo.app.entity;

import java.util.List;

/**
 * 结果响应
 * Date: 2016-12-18
 *
 * @author zhangbinalan
 */
public class ContactClassListResponse {
    private int total;
    private List<Data> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        private int contact_class_id;
        private String contact_class_name;
        private int source_type;

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

        public int getSource_type() {
            return source_type;
        }

        public void setSource_type(int source_type) {
            this.source_type = source_type;
        }
    }
}
