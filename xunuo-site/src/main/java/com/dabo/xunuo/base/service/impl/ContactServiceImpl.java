package com.dabo.xunuo.base.service.impl;

import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.dao.ContactFigurePropMapper;
import com.dabo.xunuo.base.dao.ContactMapper;
import com.dabo.xunuo.base.dao.ContactPropMapper;
import com.dabo.xunuo.base.dao.ContactTypeMapper;
import com.dabo.xunuo.base.entity.Contact;
import com.dabo.xunuo.base.entity.ContactFigureProp;
import com.dabo.xunuo.base.entity.ContactFigurePropValues;
import com.dabo.xunuo.base.entity.ContactProp;
import com.dabo.xunuo.base.entity.ContactType;
import com.dabo.xunuo.base.service.IContactService;
import com.dabo.xunuo.base.service.IUserEventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements IContactService {
    @Autowired
    private ContactFigurePropMapper contactFigurePropMapper;

    @Autowired
    private ContactTypeMapper contactTypeMapper;

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private ContactPropMapper contactPropMapper;

    @Autowired
    private IUserEventService userEventService;

    @Override
    public List<ContactType> getUserContactType(long userId) throws SysException {
        //首先获取系统指定的全部的类型
        List<ContactType> systemTypeList = contactTypeMapper.getContactTypeByUser(0, ContactType.SOURCE_SYSTEM, "sort_index", "asc", null);
        //获取用户指定的全部的类型
        List<ContactType> userContactTypeList = contactTypeMapper.getContactTypeByUser(userId, ContactType.SOURCE_USER, "sort_index", "asc", null);

        List<ContactType> resultList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(systemTypeList)) {
            resultList.addAll(systemTypeList);
        }

        if (!CollectionUtils.isEmpty(userContactTypeList)) {
            resultList.addAll(userContactTypeList);
        }
        return resultList;
    }

    @Override
    public void createContactType(ContactType contactType) throws SysException {
        long userId = contactType.getUserId();
        int sourceType = contactType.getSourceType();

        contactType.setCreateTime(System.currentTimeMillis());
        contactType.setUpdateTime(contactType.getCreateTime());

        if (contactType.getSortIndex() == 0) {
            int countTotal = contactTypeMapper.countByUser(userId, sourceType);
            int maxSortIndex = 0;
            if (countTotal > 0) {
                maxSortIndex = contactTypeMapper.getMaxSortIndex(userId, sourceType);
            }
            contactType.setSortIndex(maxSortIndex + 1);
            contactTypeMapper.insert(contactType);
        }
    }

    @Override
    public void createContact(Contact contact) throws SysException {
        long currentTime = System.currentTimeMillis();
        contact.setCreateTime(currentTime);
        contact.setUpdateTime(currentTime);
        //新建联系人
        contactMapper.insert(contact);
    }

    @Override
    public void updateContact(Contact contact) throws SysException {
        contact.setUpdateTime(System.currentTimeMillis());
        //修改数据
        contactMapper.update(contact);
    }

    @Override
    public Contact getContactById(long contactId) throws SysException {
        Contact contact = contactMapper.getById(contactId);
        return contact;
    }

    @Override
    public void deleteContactById(long contactId) throws SysException {
        //删除关联的事件
        userEventService.deleteUserEventByContactId(contactId);
        //联系人形象属性不删除
        //contactFigurePropMapper.deleteByContact(contactId);
        //联系人的自定义属性不删除
        //contactPropMapper.deleteByContact(contactId);
        //联系人设置状态为删除
        contactMapper.setState(contactId, Contact.STATE_DELETE, System.currentTimeMillis());
    }

    @Override
    public List<Contact> getContactByIds(List<Long> contactIds) throws SysException {
        if (CollectionUtils.isEmpty(contactIds)) {
            return Collections.emptyList();
        }
        contactIds = contactIds.stream().distinct().collect(Collectors.toList());
        return contactMapper.getListByIds(contactIds);
    }

    @Override
    public List<Contact> getContactByUser(long userId) throws SysException {
        List<Contact> contactList = contactMapper.getAllByUser(userId, Contact.STATE_NORMAL);
        return contactList;
    }

    @Override
    public Map<Long, ContactFigurePropValues> getContactFigurePropValue(List<Long> contactIds) {
        Map<Long, ContactFigurePropValues> contactId2FigureValuesMap = new HashMap<>();
        List<ContactFigureProp> allFigureValueList = contactFigurePropMapper.getByContacts(contactIds);
        if (!CollectionUtils.isEmpty(allFigureValueList)) {
            Map<Long, TreeSet<ContactFigureProp>> id2FigureValueMap = new HashMap<>();
            Map<Long, TreeSet<ContactFigureProp>> id2SelfFigureValueMap = new HashMap<>();
            allFigureValueList.forEach(contactFigure -> {
                TreeSet<ContactFigureProp> contactFigurePropValueSet;
                if (contactFigure.getPropType() == ContactFigureProp.SYSTEM_DEDINE) {
                    contactFigurePropValueSet = id2FigureValueMap.get(contactFigure.getContactId());
                } else {
                    contactFigurePropValueSet = id2SelfFigureValueMap.get(contactFigure.getContactId());
                }
                if (contactFigurePropValueSet == null) {
                    contactFigurePropValueSet = new TreeSet<>((ContactFigureProp o1, ContactFigureProp o2) -> (int) (o1.getId() - o2.getId()));
                    contactFigurePropValueSet.add(contactFigure);
                    if (contactFigure.getPropType() == ContactFigureProp.SYSTEM_DEDINE) {
                        id2FigureValueMap.put(contactFigure.getContactId(), contactFigurePropValueSet);
                    } else {
                        id2SelfFigureValueMap.put(contactFigure.getContactId(), contactFigurePropValueSet);
                    }
                } else {
                    contactFigurePropValueSet.add(contactFigure);
                }

            });
            contactIds.forEach(contactId -> {
                TreeSet<ContactFigureProp> figureValueSet = id2FigureValueMap.get(contactId);
                ContactFigurePropValues propValues = new ContactFigurePropValues();
                if (figureValueSet != null) {
                    propValues.setFigurePropValueList(figureValueSet.stream().collect(Collectors.toList()));
                }
                TreeSet<ContactFigureProp> figureSelfValueSet = id2SelfFigureValueMap.get(contactId);
                if (figureValueSet != null) {
                    propValues.setSelfPropValueList(figureSelfValueSet.stream().collect(Collectors.toList()));
                }
                contactId2FigureValuesMap.put(contactId, propValues);
            });
        }
        return contactId2FigureValuesMap;
    }

    @Override
    public Map<Long, List<ContactProp>> getContactProp(List<Long> contactIds) {
        Map<Long, List<ContactProp>> id2InfoMap = new HashMap<>();
        List<ContactProp> contactProps = contactPropMapper.getByContacts(contactIds);
        if (!CollectionUtils.isEmpty(contactProps)) {
            Map<Long, TreeSet<ContactProp>> id2PropMap = new HashMap<>();
            contactProps.forEach(contactProp -> {
                TreeSet<ContactProp> contactPropSet = id2PropMap.get(contactProp.getContactId());
                if (contactPropSet == null) {
                    contactPropSet = new TreeSet<>((ContactProp o1, ContactProp o2) -> (int) (o1.getId() - o2.getId()));
                    contactPropSet.add(contactProp);
                    id2PropMap.put(contactProp.getContactId(), contactPropSet);
                } else {
                    contactPropSet.add(contactProp);
                }
            });
            contactIds.forEach(contactId -> {
                TreeSet<ContactProp> contactPropTreeSet = id2PropMap.get(contactId);
                if (contactPropTreeSet != null) {
                    id2InfoMap.put(contactId, contactPropTreeSet.stream().collect(Collectors.toList()));
                }
            });
        }
        return id2InfoMap;
    }

    @Override
    public Map<Integer, ContactType> getContactType(List<Integer> contactTypeIds) {
        Map<Integer, ContactType> id2InfoMap = new HashMap<>();
        if (CollectionUtils.isEmpty(contactTypeIds)) {
            return id2InfoMap;
        }
        List<ContactType> contactTypeList = contactTypeMapper.getListByIds(contactTypeIds);
        if (!CollectionUtils.isEmpty(contactTypeList)) {
            contactTypeList.forEach(contactType -> id2InfoMap.put(contactType.getId(), contactType));
        }
        return id2InfoMap;
    }

    @Override
    public void setContactProp(long contactId, List<ContactProp> contactPropList) {
        //清理旧的
        contactPropMapper.deleteByContact(contactId);
        //插入新的
        if (CollectionUtils.isEmpty(contactPropList)) {
            contactPropList.forEach(contactProp -> contactProp.setContactId(contactId));
            contactPropMapper.insertBatch(contactPropList);
        }
    }

    @Override
    public void setContactFigurePropValue(long contactId, ContactFigurePropValues propValues) {
        //清理旧的
        contactFigurePropMapper.deleteByContact(contactId);
        List<ContactFigureProp> propValueList = new ArrayList();
        if (!CollectionUtils.isEmpty(propValues.getFigurePropValueList())) {
            propValueList.addAll(propValues.getFigurePropValueList());
        }
        if (!CollectionUtils.isEmpty(propValues.getSelfPropValueList())) {
            propValueList.addAll(propValues.getSelfPropValueList());
        }
        if (!CollectionUtils.isEmpty(propValueList)) {
            contactFigurePropMapper.insertBatch(propValueList);
        }
    }

    @Override
    public Map<Long, Contact> getContactMapByIds(List<Long> contactIds) throws SysException {
        Map<Long, Contact> dataMap = new HashMap<>();
        List<Contact> dataList = getContactByIds(contactIds);
        if (!CollectionUtils.isEmpty(dataList)) {
            dataList.forEach(contact -> dataMap.put(contact.getId(), contact));
        }
        return dataMap;
    }
}
