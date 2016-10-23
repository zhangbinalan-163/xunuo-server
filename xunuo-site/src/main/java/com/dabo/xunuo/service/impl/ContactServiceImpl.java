package com.dabo.xunuo.service.impl;

import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.dao.*;
import com.dabo.xunuo.entity.*;
import com.dabo.xunuo.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SystemPropertyUtils;

import java.util.*;

@Service
public class ContactServiceImpl implements IContactService {
	@Autowired
	private ContactFigureMapper contactFigureMapper;

	@Autowired
	private ContactFigureProMapper contactFigureProMapper;

	@Autowired
	private ContactFigureProValueMapper contactFigureProValueMapper;

	@Autowired
	private ContactTypeMapper contactTypeMapper;

	@Autowired
	private ContactMapper contactMapper;

	@Autowired
	private ContactProMapper contactProMapper;

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

	@Override
	public ContactType getUserContactTypeById(long contactTypeId) throws SysException {
		return contactTypeMapper.getById(contactTypeId);
	}

	@Override
	public void deleteContactType(ContactType contactType) throws SysException {
		contactTypeMapper.delete(contactType.getId());
	}

	@Override
	public ContactFigure getContactFigure(int figureId) throws SysException {
		ContactFigure contactFigure = contactFigureMapper.getById(figureId);
		return contactFigure;
	}

	@Override
	public List<ContactFigureProValue> getFigureProWithValue(long contactId, int figureId) {
		List<ContactFigureProValue> proValueList=new ArrayList<>();
		//获取联系人是否关联了该形象
		Contact contactInfo = contactMapper.getById(contactId);
		List<ContactFigurePro> figureProList=contactFigureProMapper.getProByFigure(figureId);
		if(contactInfo==null||contactInfo.getFigureId()!=figureId){
			//没有关联该形象,直接返回形象的属性
			if(!CollectionUtils.isEmpty(figureProList)){
				for (ContactFigurePro contactFigurePro : figureProList) {
					ContactFigureProValue contactFigureProValue=new ContactFigureProValue();
					contactFigureProValue.setFigurePropId(contactFigurePro.getId());
					contactFigureProValue.setContactFigurePro(contactFigurePro);
					contactFigureProValue.setContactId(contactId);
					proValueList.add(contactFigureProValue);
				}
			}
		}else{
			//关联了形象,查找联系人设置的属性
			if(!CollectionUtils.isEmpty(figureProList)){
				List<Integer> figureProIdList=new ArrayList<>();
				figureProList.forEach(contactFigurePro -> figureProIdList.add(contactFigurePro.getId()));
				//
				proValueList=contactFigureProValueMapper.getProValueByContact(contactId,figureProIdList);
				//顺序调整
				Map<Integer,ContactFigureProValue> proId2ValueMap=new HashMap<>();
				if(!CollectionUtils.isEmpty(proValueList)){
					proValueList.forEach(figureProValue-> proId2ValueMap.put(figureProValue.getFigurePropId(),figureProValue));
				}
				List<ContactFigureProValue> proValueListInSort=new ArrayList<>();
				Map<Integer,ContactFigurePro> proId2InfoMap=new HashMap<>();
				figureProList.forEach(contactFigurePro -> {
					ContactFigureProValue proValue = proId2ValueMap.get(contactFigurePro.getId());
					if(proValue==null){
						proValue=new ContactFigureProValue();
						proValue.setFigurePropId(contactFigurePro.getId());
						proValue.setContactId(contactId);
					}
					proValueListInSort.add(proValue);
					proId2InfoMap.put(contactFigurePro.getId(),contactFigurePro);
				});
				//设置属性字段
				proValueListInSort.forEach(contactFigureProValue -> contactFigureProValue.setContactFigurePro(proId2InfoMap.get(contactFigureProValue.getFigurePropId())));
				proValueList=proValueListInSort;
			}
		}
		return proValueList;
	}

	@Override
	public void setContactFigureProValue(long contactId, int figureId,List<ContactFigureProValue> proValueList) throws SysException {
		Contact contactInfo = contactMapper.getById(contactId);
		if(contactInfo==null){
			return;
		}
		int oldFigureId=contactInfo.getFigureId();
		if(oldFigureId!=figureId){
			//修改figureId
			contactMapper.setFigureId(contactId,figureId, System.currentTimeMillis());
		}
		//删除之前的属性值设置
		List<ContactFigurePro> figureProList = contactFigureProMapper.getProByFigure(oldFigureId);
		if(!CollectionUtils.isEmpty(figureProList)){
			List<Integer> figureProIdList=new ArrayList<>();
			figureProList.forEach(figurePro -> figureProIdList.add(figurePro.getId()));
			//删除
			contactFigureProValueMapper.deleteProValueByContact(contactId,figureProIdList);
		}
		//设置新的
		contactFigureProValueMapper.insertBatch(proValueList);
		//设置修改时间
		contactMapper.setUpdateTime(contactId,System.currentTimeMillis());
	}

	@Override
	public long countByContactType(long contactType) throws SysException {
		return contactMapper.countByType(contactType);
	}

	@Override
	public void batchCreateContact(List<Contact> contactList) throws SysException {
		if(!CollectionUtils.isEmpty(contactList)){
			for (Contact contact : contactList) {
				createContact(contact);
			}
		}
	}

	@Override
	public void createContact(Contact contact) throws SysException {
		long currentTime=System.currentTimeMillis();
		contact.setCreateTime(currentTime);
		contact.setUpdateTime(currentTime);
		//新建联系人
		contactMapper.insert(contact);
		//如果有自定义字段
		if(!CollectionUtils.isEmpty(contact.getContactProList())){
			contactProMapper.insertBatch(contact.getContactProList());
		}
	}

	@Override
	public void updateContact(Contact contact) throws SysException {

		contact.setUpdateTime(System.currentTimeMillis());
		//修改数据
		contactMapper.update(contact);
		//删除旧的自定义字段
		contactProMapper.delete(contact.getId());
		//添加新的自定义字段
		if(!CollectionUtils.isEmpty(contact.getContactProList())){
			contactProMapper.insertBatch(contact.getContactProList());
		}
	}

	@Override
	public Contact getContactById(long contactId) throws SysException {
		Contact contact=contactMapper.getById(contactId);
		//
		if(contact!=null){
			List<ContactPro> proList = contactProMapper.getProByContact(contactId);
			contact.setContactProList(proList);
		}
		return contact;
	}

	@Override
	public void deleteContactById(long contactId) throws SysException {
		//删除联系人的自定义属性
		contactProMapper.deleteProByContact(contactId);
		//删除联系人
		contactMapper.delete(contactId);
	}
}
