package com.dabo.xunuo.web.vo;


import java.util.Date;
import java.util.List;

/**
 * 批量新增联系人请求
 * Created by zhangbinalan on 2016/10/23.
 */
public class ContactBatchCreateParam {
    private List<ContactEntity> contacts;

    public List<ContactEntity> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactEntity> contacts) {
        this.contacts = contacts;
    }

    public static class ContactEntity{
        private long id;
        private String head_url;
        private int figure_id;
        private String name;
        private int type_id;
        private Date birth;
        private String phone;
        private String remark;
        private List<SelfPropEntity> self_prop;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getHead_url() {
            return head_url;
        }

        public void setHead_url(String head_url) {
            this.head_url = head_url;
        }

        public int getFigure_id() {
            return figure_id;
        }

        public void setFigure_id(int figure_id) {
            this.figure_id = figure_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public Date getBirth() {
            return birth;
        }

        public void setBirth(Date birth) {
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

        public List<SelfPropEntity> getSelf_prop() {
            return self_prop;
        }

        public void setSelf_prop(List<SelfPropEntity> self_prop) {
            this.self_prop = self_prop;
        }
    }
    public static class SelfPropEntity{
        private long id;
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

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }
}
