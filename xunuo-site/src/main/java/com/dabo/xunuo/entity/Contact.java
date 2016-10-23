package com.dabo.xunuo.entity;

import java.util.Date;
import java.util.List;

public class Contact {
	private int id;
	private String name;
	private long birthday;
	private String phone;
	private String remark;
	private String headImg;
	private int figureId;
	private int contactTypeId;
	private List<ContactPro> contactProList;
	private long createTime;
	private long updateTime;

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
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

	public long getBirthday() {
		return birthday;
	}

	public void setBirthday(long birthday) {
		this.birthday = birthday;
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

	public int getFigureId() {
		return figureId;
	}

	public void setFigureId(int figureId) {
		this.figureId = figureId;
	}

	public int getContactTypeId() {
		return contactTypeId;
	}

	public void setContactTypeId(int contactTypeId) {
		this.contactTypeId = contactTypeId;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public List<ContactPro> getContactProList() {
		return contactProList;
	}

	public void setContactProList(List<ContactPro> contactProList) {
		this.contactProList = contactProList;
	}
}
