package com.dabo.xunuo.entity;

import java.util.List;

public class ContactType {
	private int id;
	private String name;
	private String source_type;
	private String user_id;
	private Long create_time;
	private Long update_time;
	private List<Contact>ContactList;
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
	public String getSource_type() {
		return source_type;
	}
	public void setSource_type(String source_type) {
		this.source_type = source_type;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public Long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}
	public Long getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Long update_time) {
		this.update_time = update_time;
	}
	public List<Contact> getContactList() {
		return ContactList;
	}
	public void setContactList(List<Contact> contactList) {
		ContactList = contactList;
	}

   
}
