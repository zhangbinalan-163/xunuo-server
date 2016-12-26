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
public class ContactListResponse {
    private List<Type> types;

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public static class Type {
        private int contact_class_id;
        private String contact_class_name;
        private List<Contact> contacts;

        public List<Contact> getContacts() {
            return contacts;
        }

        public void setContacts(List<Contact> contacts) {
            this.contacts = contacts;
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
    }

    public static class Contact {
        private long contact_id;
        private String name;
        private String head_url;
        private EventData event;

        public String getHead_url() {
            return head_url;
        }

        public void setHead_url(String head_url) {
            this.head_url = head_url;
        }

        public EventData getEvent() {
            return event;
        }

        public void setEvent(EventData event) {
            this.event = event;
        }

        public long getContact_id() {
            return contact_id;
        }

        public void setContact_id(long contact_id) {
            this.contact_id = contact_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
