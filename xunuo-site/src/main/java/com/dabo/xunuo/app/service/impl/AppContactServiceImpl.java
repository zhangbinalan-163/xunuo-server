/**
 * Copyright (C) 2006-2016 Tuniu All rights reserved
 */
package com.dabo.xunuo.app.service.impl;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dabo.xunuo.app.entity.ContactClassListResponse;
import com.dabo.xunuo.app.entity.ContactDetailResponse;
import com.dabo.xunuo.app.entity.ContactListResponse;
import com.dabo.xunuo.app.entity.ContactUpdateReq;
import com.dabo.xunuo.app.entity.EventData;
import com.dabo.xunuo.app.service.AppContactService;
import com.dabo.xunuo.app.web.vo.FigurePropReq;
import com.dabo.xunuo.base.common.Constants;
import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.entity.Contact;
import com.dabo.xunuo.base.entity.ContactFigureProp;
import com.dabo.xunuo.base.entity.ContactFigurePropValues;
import com.dabo.xunuo.base.entity.ContactNextEvent;
import com.dabo.xunuo.base.entity.ContactProp;
import com.dabo.xunuo.base.entity.ContactType;
import com.dabo.xunuo.base.entity.UserEvent;
import com.dabo.xunuo.base.entity.UserEventClass;
import com.dabo.xunuo.base.service.IContactService;
import com.dabo.xunuo.base.service.IUserEventService;
import com.dabo.xunuo.base.util.StringUtils;
import com.dabo.xunuo.base.util.TimeUtils;

@Service
public class AppContactServiceImpl implements AppContactService {
    @Autowired
    private IContactService contactService;

    @Autowired
    private IUserEventService userEventService;

    @Override
    public ContactClassListResponse getContactClassByUser(long userId) throws SysException {
        ContactClassListResponse response = new ContactClassListResponse();

        List<ContactType> contactTypeList = contactService.getUserContactType(userId);
        response.setTotal(contactTypeList.size());
        List<ContactClassListResponse.Data> dataList = new ArrayList<>();
        contactTypeList.forEach(contactType -> {
            ContactClassListResponse.Data data = new ContactClassListResponse.Data();
            data.setContact_class_id(contactType.getId());
            data.setContact_class_name(contactType.getName());
            data.setSource_type(contactType.getSourceType());
            dataList.add(data);
        });
        response.setData(dataList);
        return response;
    }

    @Override
    public ContactDetailResponse getContactDetail(long contactId) throws SysException {
        Contact contactInfo = contactService.getContactById(contactId);

        ContactDetailResponse response = new ContactDetailResponse();
        if (contactInfo != null) {
            response.setContact_id(contactInfo.getId());
            response.setHead_url(contactInfo.getHeadImg());
            response.setName(contactInfo.getName());
            response.setBirth(TimeUtils.getDateStrWithoutTime(contactInfo.getBirthday()));
            response.setPhone(contactInfo.getPhone());
            response.setRemark(contactInfo.getRemark());
            //类别
            ContactType contactTypeInfo = ContactType.defaultType();
            if (contactInfo.getContactTypeId() != 0) {
                Map<Integer, ContactType> typeId2InfoMap = contactService.getContactType(Arrays.asList(contactInfo.getContactTypeId()));
                contactTypeInfo = typeId2InfoMap.get(contactInfo.getContactTypeId());
            }
            response.setContact_class_id(contactTypeInfo.getId());
            response.setContact_class_name(contactTypeInfo.getName());
            //自定义属性
            Map<Long, List<ContactProp>> contactId2PropMap = contactService.getContactProp(Arrays.asList(contactId));
            List<ContactProp> selfProps = contactId2PropMap.get(contactId);
            if (!CollectionUtils.isEmpty(selfProps)) {
                List<ContactDetailResponse.SelfProp> selfPropList = new ArrayList<>();
                if (!CollectionUtils.isEmpty(selfProps)) {
                    selfProps.forEach(contactProp -> {
                        ContactDetailResponse.SelfProp selfProp = new ContactDetailResponse.SelfProp();
                        selfProp.setName(contactProp.getName());
                        selfProp.setValue(contactProp.getValue());
                        selfPropList.add(selfProp);
                    });
                }
                response.setSelf_prop(selfPropList);
            }
            //形象信息
            if (contactInfo.getFigureId() >= 0) {
                ContactDetailResponse.FigureWithValue figureWithValue = new ContactDetailResponse.FigureWithValue();
                figureWithValue.setFigure_id(contactInfo.getFigureId());
                figureWithValue.setFigure_type(contactInfo.getFigureId());
                Map<Long, ContactFigurePropValues> contactId2ValueMap = contactService.getContactFigurePropValue(Arrays.asList(contactId));
                ContactFigurePropValues figurePropValue = contactId2ValueMap.get(contactId);
                if (figurePropValue != null) {
                    if (!CollectionUtils.isEmpty(figurePropValue.getFigurePropValueList())) {
                        List<ContactDetailResponse.FigureProp> defaultList = new ArrayList<>();
                        figurePropValue.getFigurePropValueList().forEach(contactFigureProp -> {
                            defaultList.add(conventVo(contactFigureProp));
                        });
                        figureWithValue.setDefault_key_values(defaultList);
                    }
                    if (!CollectionUtils.isEmpty(figurePropValue.getSelfPropValueList())) {
                        List<ContactDetailResponse.FigureProp> selfList = new ArrayList<>();
                        figurePropValue.getSelfPropValueList().forEach(contactFigureProp -> {
                            selfList.add(conventVo(contactFigureProp));
                        });
                        figureWithValue.setExtra_key_values(selfList);
                    }
                }
                response.setFigure(figureWithValue);
            }
            //事件
            Map<Long, ContactNextEvent> contactId2EventMap = userEventService.getContactNextEvent(Arrays.asList(contactId));
            ContactNextEvent nextEvent = contactId2EventMap.get(contactId);
            if (nextEvent != null) {
                UserEvent event = userEventService.getUserEventById(nextEvent.getEventId());
                if (event != null) {
                    EventData eventData = new EventData();
                    eventData.setEvent_id(event.getId());
                    eventData.setName(event.getName());
                    eventData.setDays_remain(TimeUtils.getRemainDays(nextEvent.getNextEventTime()));
                    eventData.setNext_time(TimeUtils.getDateStrWithoutTime(nextEvent.getNextEventTime()));
                    UserEvent userEventInfo = userEventService.getUserEventById(nextEvent.getEventId());
                    if (userEventInfo != null) {
                        eventData.setEvent_time(TimeUtils.getDateStrWithoutTime(userEventInfo.getEventTime()));
                    }
                    response.setEvent(eventData);
                }
            }
        }
        return response;
    }

    private ContactDetailResponse.FigureProp conventVo(ContactFigureProp contactFigureProp) {
        ContactDetailResponse.FigureProp figureProp = new ContactDetailResponse.FigureProp();
        figureProp.setProp_name(contactFigureProp.getProp());
        figureProp.setProp_value(contactFigureProp.getValue());
        return figureProp;
    }

    @Override
    public ContactListResponse getContactList(long userId) throws SysException {
        ContactListResponse response = new ContactListResponse();
        List<Contact> contactList = contactService.getContactByUser(userId);
        if (!CollectionUtils.isEmpty(contactList)) {
            List<Integer> contactTypeIdList = new ArrayList<>();
            List<Long> contactIdList = new ArrayList<>();
            contactList.forEach(contact -> {
                contactTypeIdList.add(contact.getContactTypeId());
                contactIdList.add(contact.getId());
            });

            Map<Integer, List<Contact>> type2ContactListMap = sortContact(contactList);
            List<ContactType> sortedTypeList = sortContactType(contactTypeIdList);
            //事件
            Map<Long, ContactNextEvent> contactId2EventMap = userEventService.getContactNextEvent(contactIdList);
            List<Long> eventIdList = new ArrayList<>();
            contactId2EventMap.values().forEach(contactNextEvent -> {
                if (contactNextEvent != null) {
                    eventIdList.add(contactNextEvent.getEventId());
                }
            });
            Map<Long, UserEvent> eventId2EventMap = (!CollectionUtils.isEmpty(eventIdList) ? userEventService.getUserEventByIds(eventIdList) : new HashMap<>());

            List<ContactListResponse.Type> typesVo = new ArrayList<>();

            if (!CollectionUtils.isEmpty(sortedTypeList)) {
                sortedTypeList.forEach(contactType -> {
                    ContactListResponse.Type typeVo = new ContactListResponse.Type();
                    typeVo.setContact_class_id(contactType.getId());
                    typeVo.setContact_class_name(contactType.getName());
                    List<Contact> sortedContactList = type2ContactListMap.get(contactType.getId());
                    List<ContactListResponse.Contact> contactVoList = new ArrayList<>();
                    if (!CollectionUtils.isEmpty(sortedContactList)) {
                        sortedContactList.forEach(contact -> {
                            ContactListResponse.Contact contactVo = new ContactListResponse.Contact();
                            contactVo.setName(contact.getName());
                            contactVo.setContact_id(contact.getId());
                            contactVo.setHead_url(contact.getHeadImg());
                            ContactNextEvent event = contactId2EventMap.get(contact.getId());
                            if (event != null) {
                                UserEvent eventInfo = eventId2EventMap.get(event.getEventId());
                                if (event != null && eventInfo != null) {
                                    EventData nextEvent = new EventData();
                                    nextEvent.setName(eventInfo.getName());
                                    nextEvent.setDays_remain(TimeUtils.getRemainDays(event.getNextEventTime()));
                                    nextEvent.setEvent_id(event.getId());
                                    contactVo.setEvent(nextEvent);
                                }
                            }
                            contactVoList.add(contactVo);
                        });
                    }
                    typeVo.setContacts(contactVoList);
                    if (!CollectionUtils.isEmpty(contactVoList)) {
                        typesVo.add(typeVo);
                    }
                });
            }
            response.setTypes(typesVo);
        }
        return response;
    }

    private List<ContactType> sortContactType(List<Integer> contactTypeIdList) {
        Map<Integer, ContactType> id2InfoMap = contactService.getContactType(contactTypeIdList);
        TreeSet<ContactType> treeSet = new TreeSet<>((ContactType o1, ContactType o2) -> {
            Collator instance = Collator.getInstance(Locale.CHINA);
            return instance.compare(o1.getName(), o2.getName());
        });
        Set<Map.Entry<Integer, ContactType>> entrySet = id2InfoMap.entrySet();
        entrySet.forEach(entry -> {
            if (entry.getValue() != null) {
                treeSet.add(entry.getValue());
            }
        });

        List<ContactType> typeList = treeSet.stream().collect(Collectors.toList());
        //默认的
        typeList.add(ContactType.defaultType());
        return typeList;
    }

    private Map<Integer, List<Contact>> sortContact(List<Contact> allContactList) {
        Map<Integer, List<Contact>> type2ContactListMap = new HashMap<>();
        Map<Integer, TreeSet<Contact>> typeId2ContactMap = new HashMap<>();
        allContactList.forEach(contact -> {
            TreeSet<Contact> contactSet = typeId2ContactMap.get(contact.getContactTypeId());
            if (CollectionUtils.isEmpty(contactSet)) {
                contactSet = new TreeSet<>((Contact o1, Contact o2) -> {
                    Collator instance = Collator.getInstance(Locale.CHINA);
                    return instance.compare(o1.getName(), o2.getName());
                });
                contactSet.add(contact);
                typeId2ContactMap.put(contact.getContactTypeId(), contactSet);
            } else {
                contactSet.add(contact);
            }
        });
        Set<Map.Entry<Integer, TreeSet<Contact>>> entrySet = typeId2ContactMap.entrySet();
        entrySet.forEach(entry -> {
            if (!CollectionUtils.isEmpty(entry.getValue())) {
                type2ContactListMap.put(entry.getKey(), entry.getValue().stream().collect(Collectors.toList()));
            }
        });
        return type2ContactListMap;
    }

    @Override
    public void deleteContact(long contactId, long currentUser) throws SysException {
        Contact contactInfo = contactService.getContactById(contactId);
        if (contactInfo == null) {
            throw new SysException("数据不存在", Constants.ERROR_CODE_DATA_NO_RIGHT);
        }
        contactService.deleteContactById(contactId);
    }

    @Override
    public void updateContactBatch(List<ContactUpdateReq> contactUpdateReqList) throws SysException {
        if (!CollectionUtils.isEmpty(contactUpdateReqList)) {
            for (ContactUpdateReq contactUpdateReq : contactUpdateReqList) {
                updateContact(contactUpdateReq);
            }
        }
    }

    @Override
    public long updateContact(ContactUpdateReq contactUpdateReq) throws SysException {
        Contact contact = parseContact(contactUpdateReq);
        int contactClassId = contactUpdateReq.getContact_class_id();
        String contactClassName = contactUpdateReq.getContact_class_name();
        if (contactClassId == 0 && !StringUtils.isEmpty(contactClassName)) {
            //新增类别
            ContactType contactType = new ContactType();
            contactType.setUserId(contact.getUserId());
            contactType.setSourceType(ContactType.SOURCE_USER);
            contactType.setName(contactClassName);
            contactService.createContactType(contactType);
            contactClassId = contactType.getId();
        }
        contact.setContactTypeId(contactClassId);
        if (contact.getId() == 0) {
            contactService.createContact(contact);
            //生成生日事件,修改基础表
            createUserBirthDayEvent(contact);
        } else {
            //如果生日改了,删除旧的生日事件,创建新的
            Contact oldContact = contactService.getContactById(contact.getId());
            if (oldContact != null && oldContact.getBirthday() != contact.getBirthday()) {
                UserEvent userEvent = new UserEvent();
                userEvent.setContactId(oldContact.getId());
                userEvent.setId(oldContact.getBirthdayEventId());
                userEvent.setUserId(oldContact.getUserId());
                userEventService.deleteUserEvent(userEvent);

                UserEvent userBirthEvent = new UserEvent();
                userBirthEvent.setUserId(contact.getUserId());
                userBirthEvent.setContactId(contact.getId());
                userBirthEvent.setEventTime(contact.getBirthday());
                userBirthEvent.setName("生日");
                userBirthEvent.setEventClass(5);//固定的写死的生日类型5
                userBirthEvent.setChineseCalendarFlag(UserEvent.CHINESE_CALENDAR_YES);
                userBirthEvent.setUserEventClass(userEventService.getEventClass(5));

                userEventService.createUserEvent(userBirthEvent);

                contact.setBirthdayEventId(userBirthEvent.getId());
            }
            contactService.updateContact(contact);
        }
        //自定义形象
        List<ContactProp> selfPropList = contactUpdateReq.getSelf_prop();
        contactService.setContactProp(contact.getId(), selfPropList);

        return contact.getId();
    }

    private void createUserBirthDayEvent(Contact contact) throws SysException {
        UserEvent userEvent = new UserEvent();
        userEvent.setUserId(contact.getUserId());
        userEvent.setContactId(contact.getId());
        userEvent.setEventTime(contact.getBirthday());
        userEvent.setName("生日");
        userEvent.setEventClass(5);//固定的写死的生日类型5
        userEvent.setChineseCalendarFlag(UserEvent.CHINESE_CALENDAR_YES);
        userEvent.setUserEventClass(userEventService.getEventClass(5));

        userEventService.createUserEvent(userEvent);

        Contact contactInfo = contactService.getContactById(contact.getId());
        contactInfo.setBirthdayEventId(userEvent.getId());
        contactService.updateContact(contactInfo);
    }

    private Contact parseContact(ContactUpdateReq contactAddEntity) {
        Contact contact = new Contact();
        contact.setId(contactAddEntity.getContact_id());
        contact.setBirthday(contactAddEntity.getBirth() == null ? 0 : contactAddEntity.getBirth().getTime());
        contact.setName(contactAddEntity.getName());
        contact.setHeadImg(contactAddEntity.getHead_url());
        contact.setRemark(contactAddEntity.getRemark());
        contact.setPhone(contactAddEntity.getPhone());
        contact.setFigureId(contactAddEntity.getFigure_id());
        contact.setContactTypeId(contactAddEntity.getContact_class_id());
        contact.setUserId(contactAddEntity.getUser_id());
        return contact;
    }

    @Override
    public void updateContactFigureProp(FigurePropReq figurePropReq, long currentUser) throws SysException {
        Contact contact = contactService.getContactById(figurePropReq.getContact_id());
        if (contact == null) {
            throw new SysException("数据不存在", Constants.ERROR_CODE_DATA_NOT_EXSIST);
        }
        if (contact.getUserId() != currentUser) {
            throw new SysException("无权限", Constants.ERROR_CODE_DATA_NO_RIGHT);
        }

        List<FigurePropReq.PropVo> defaultPropValues = figurePropReq.getDefault_key_values();
        List<ContactFigureProp> defaultFigueProps = new ArrayList<>();
        if (!CollectionUtils.isEmpty(defaultPropValues)) {
            defaultPropValues.forEach(propVo -> {
                ContactFigureProp contactFigurProp = new ContactFigureProp();
                contactFigurProp.setContactId(contact.getId());
                contactFigurProp.setProp(propVo.getProp_name());
                contactFigurProp.setValue(propVo.getProp_value());
                contactFigurProp.setPropType(ContactFigureProp.SYSTEM_DEDINE);
                defaultFigueProps.add(contactFigurProp);
            });
        }

        List<FigurePropReq.PropVo> extralFigureProps = figurePropReq.getExtra_key_values();
        List<ContactFigureProp> userFigueProps = new ArrayList<>();
        if (!CollectionUtils.isEmpty(extralFigureProps)) {
            extralFigureProps.forEach(propVo -> {
                ContactFigureProp contactFigurProp = new ContactFigureProp();
                contactFigurProp.setContactId(contact.getId());
                contactFigurProp.setProp(propVo.getProp_name());
                contactFigurProp.setValue(propVo.getProp_value());
                contactFigurProp.setPropType(ContactFigureProp.USER_DEDINE);
                userFigueProps.add(contactFigurProp);
            });
        }
        ContactFigurePropValues contactFigurePropValues = new ContactFigurePropValues();
        contactFigurePropValues.setSelfPropValueList(userFigueProps);
        contactFigurePropValues.setFigurePropValueList(defaultFigueProps);
        contactService.setContactFigurePropValue(figurePropReq.getContact_id(), contactFigurePropValues);
        //修改形象ID
        if (contact.getFigureId() != figurePropReq.getFigure_type()) {
            contactService.updateContactFigureId(contact.getId(), figurePropReq.getFigure_type());
        }
    }
}
