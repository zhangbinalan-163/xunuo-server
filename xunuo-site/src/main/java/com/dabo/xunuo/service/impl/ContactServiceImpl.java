package com.dabo.xunuo.service.impl;

import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.dao.ContactFigureMapper;
import com.dabo.xunuo.dao.ContactFigureProMapper;
import com.dabo.xunuo.dao.ContactTypeMapper;
import com.dabo.xunuo.entity.ContactFigure;
import com.dabo.xunuo.entity.ContactFigurePro;
import com.dabo.xunuo.entity.ContactType;
import com.dabo.xunuo.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceImpl implements IContactService {
	@Autowired
	private ContactFigureMapper contactFigureMapper;

	@Autowired
	private ContactFigureProMapper contactFigureProMapper;

	@Autowired
	private ContactTypeMapper contactTypeMapper;

	@Override
	public void createContactFigure(ContactFigure contactFigure) throws SysException {
		//插入主表
		contactFigureMapper.insert(contactFigure);
		//插入属性字段
		List<ContactFigurePro> contactFigureProList = contactFigure.getProList();
		if(!CollectionUtils.isEmpty(contactFigureProList)){
			//设置形象ID
			contactFigureProList.forEach(contactFigurePro -> contactFigurePro.setFigureId(contactFigure.getId()));
			//批量插入
			contactFigureProMapper.insertBatch(contactFigureProList);
		}
	}

	@Override
	public List<ContactFigure> getAllContactFigure() throws SysException {
		return contactFigureMapper.getAllFigure();
	}

	@Override
	public List<ContactFigurePro> getFigurePro(int figureId) throws SysException {
		return contactFigureProMapper.getProByFigure(figureId);
	}

	@Override
	public void deleteContactFigure(ContactFigure contactFigure) throws SysException {
		//删除属性
		contactFigureProMapper.deleteByFigure(contactFigure.getId());
		//删除主表
		contactFigureMapper.delete(contactFigure.getId());
	}

	@Override
	public List<ContactType> getUserContactType(long userId) throws SysException {
		//首先获取系统指定的全部的类型
		List<ContactType> systemTypeList = contactTypeMapper.getContactTypeByUser(0, ContactType.SOURCE_SYSTEM, "sort_index", "asc", null);
		//获取用户指定的全部的类型
		List<ContactType> userContactTypeList = contactTypeMapper.getContactTypeByUser(userId, ContactType.SOURCE_USER, "sort_index", "asc", null);

		List<ContactType> resultList=new ArrayList<>();
		if(!CollectionUtils.isEmpty(systemTypeList)){
			resultList.addAll(systemTypeList);
		}

		if(!CollectionUtils.isEmpty(userContactTypeList)){
			resultList.addAll(userContactTypeList);
		}
		return resultList;
	}

	@Override
	public void createContactType(ContactType contactType) throws SysException {
		long userId=contactType.getUserId();
		int sourceType=contactType.getSourceType();

		contactType.setCreateTime(System.currentTimeMillis());
		contactType.setUpdateTime(contactType.getCreateTime());

		if(contactType.getSortIndex() == 0){
			int count = contactTypeMapper.countByUser(userId, sourceType);
			int maxSortIndex=0;
			if(count > 0){
				maxSortIndex = contactTypeMapper.getMaxSortIndex(userId,sourceType);
			}
			contactType.setSortIndex(maxSortIndex+1);
			//insert
			contactTypeMapper.insert(contactType);
		}
	}
}
