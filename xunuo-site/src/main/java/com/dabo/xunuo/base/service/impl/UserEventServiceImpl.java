package com.dabo.xunuo.base.service.impl;

import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.dao.ContactNextEventMapper;
import com.dabo.xunuo.base.dao.UserEventNextTriggerMapper;
import com.dabo.xunuo.base.entity.ContactNextEvent;
import com.dabo.xunuo.base.entity.PageData;
import com.dabo.xunuo.base.entity.UserEvent;
import com.dabo.xunuo.base.entity.UserEventNextTrigger;
import com.dabo.xunuo.base.service.IContactService;
import com.dabo.xunuo.base.dao.UserEventMapper;
import com.dabo.xunuo.base.dao.UserEventClassMapper;
import com.dabo.xunuo.base.entity.Contact;
import com.dabo.xunuo.base.entity.RowBounds;
import com.dabo.xunuo.base.entity.UserEventClass;
import com.dabo.xunuo.base.service.IUserEventService;
import com.dabo.xunuo.base.util.TimeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户事件业务基础实现
 * Created by zhangbin on 16/8/28.
 */
@Service
public class UserEventServiceImpl implements IUserEventService {
    @Autowired
    private UserEventMapper userEventMapper;

    @Autowired
    private UserEventClassMapper userEventClassMapper;

    @Autowired
    private ContactNextEventMapper contactNextEventMapper;

    @Autowired
    private UserEventNextTriggerMapper userEventNextTriggerMapper;

    @Override
    public List<UserEventClass> getUserEventClass(long userId) throws SysException {
        //首先获取系统指定的全部的事件类型
        List<UserEventClass> systemEventClassList = userEventClassMapper.getEventClassByUser(0, UserEventClass.SOURCE_SYSTEM, "sort_index", "asc", null);
        //获取用户指定的全部的事件类型
        List<UserEventClass> userEventClassList = userEventClassMapper.getEventClassByUser(userId, UserEventClass.SOURCE_USER, "sort_index", "asc", null);

        List<UserEventClass> resultList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(systemEventClassList)) {
            resultList.addAll(systemEventClassList);
        }

        if (!CollectionUtils.isEmpty(userEventClassList)) {
            resultList.addAll(userEventClassList);
        }
        return resultList;
    }

    @Override
    public void createUserEventClass(UserEventClass userEventType) throws SysException {
        //TODO 增加用户最多自定义类型数量
        long userId = userEventType.getUserId();
        int sourceType = userEventType.getSourceType();
        userEventType.setCreateTime(System.currentTimeMillis());
        userEventType.setUpdateTime(userEventType.getCreateTime());

        if (userEventType.getSortIndex() == 0) {
            int count = userEventClassMapper.countByUser(userId, sourceType);
            int maxSortIndex = 0;
            if (count > 0) {
                //如果没有指定排序顺序,找到当前最大的排序值+1,高并发会有并发问题,不过暂时不考虑
                maxSortIndex = userEventClassMapper.getMaxSortIndex(userId, sourceType);
            }
            userEventType.setSortIndex(maxSortIndex + 1);
            //insert
            userEventClassMapper.insert(userEventType);
        }
        //TODO 其他情况
    }

    @Override
    public void createUserEvent(UserEvent userEvent) throws SysException {
        long currentTime = System.currentTimeMillis();
        userEvent.setCreateTime(currentTime);
        userEvent.setUpdateTime(currentTime);

        userEventMapper.insert(userEvent);
        //联系人下一次触发时间
        if (userEvent.getContactId() != 0) {
            reCalculateContactNextEvent(userEvent.getContactId(), userEvent);
        }
        //用户事件下一次触发时间
        UserEventNextTrigger userEventNextTrigger = calculateNextTrigger(userEvent);
        userEventNextTriggerMapper.insert(userEventNextTrigger);
    }

    private void reCalculateContactNextEvent(long contactId, UserEvent userEvent) {
        ContactNextEvent contactNextEvent = calculateNextEventByEvent(userEvent);
        //当前记录的最近一次事件
        ContactNextEvent currentNextEevent = getNextEventByContact(contactId);
        if (currentNextEevent != null) {
            if (currentNextEevent.getTriggerTime() > contactNextEvent.getTriggerTime()) {
                contactNextEventMapper.delete(currentNextEevent.getId());
                //下一次事件计算
                contactNextEventMapper.insert(contactNextEvent);
            }
        } else {
            contactNextEventMapper.insert(contactNextEvent);
        }
    }

    private void reCalculateContactNextEvent(long contactId) {
        //当前记录的最近一次事件
        ContactNextEvent currentNextEevent = getNextEventByContact(contactId);
        if (currentNextEevent != null) {
            contactNextEventMapper.delete(currentNextEevent.getId());
        }
        //获取用户全部事件
        List<UserEvent> allEventList = userEventMapper.getAllEventByContact(contactId, UserEvent.STATE_NORMAL);
        ContactNextEvent newNextEvent = new ContactNextEvent();
        newNextEvent.setTriggerTime(Long.MAX_VALUE);
        if (!CollectionUtils.isEmpty(allEventList)) {
            for (UserEvent userEvent : allEventList) {
                ContactNextEvent tmpNextEvent = calculateNextEventByEvent(userEvent);
                if (userEvent.getEventTime() > System.currentTimeMillis() && tmpNextEvent.getTriggerTime() < newNextEvent.getTriggerTime()) {
                    newNextEvent = tmpNextEvent;
                }
            }
        }
        if (newNextEvent.getTriggerTime() != Long.MAX_VALUE) {
            contactNextEventMapper.insert(newNextEvent);
        }
    }

    @Override
    public void updateUserEvent(UserEvent userEvent, UserEvent oldUserEvent) throws SysException {
        //先修改数据
        userEvent.setUpdateTime(System.currentTimeMillis());
        userEventMapper.update(userEvent);
        //联系人下一次数据
        long contactId = userEvent.getContactId();
        long oldContactId = oldUserEvent.getContactId();
        if (oldContactId != 0) {
            reCalculateContactNextEvent(oldContactId);
        }
        if (oldContactId != 0 && contactId != oldContactId) {
            reCalculateContactNextEvent(oldContactId);
        }
        UserEventNextTrigger nextTrigger = calculateNextTrigger(userEvent);
        //用户事件下一次触发时间
        userEventNextTriggerMapper.deleteByEventId(userEvent.getId());
        userEventNextTriggerMapper.insert(nextTrigger);
    }

    @Override
    public UserEvent getUserEventById(long eventId) throws SysException {
        return userEventMapper.getById(eventId);
    }

    @Override
    public void deleteUserEventByContactId(long contactId) throws SysException {
        //
        List<UserEvent> allEventList = userEventMapper.getAllEventByContact(contactId, Contact.STATE_NORMAL);
        if (!CollectionUtils.isEmpty(allEventList)) {
            List<Long> userEventIds = new ArrayList<>();
            allEventList.forEach(userEvent -> userEventIds.add(userEvent.getId()));
            userEventNextTriggerMapper.deleteByEventIds(userEventIds);
        }
        //删除下一个发生的事件
        contactNextEventMapper.deleteByContact(contactId);
        //设置事件状态
        userEventMapper.setStateByContact(contactId, UserEvent.STATE_DELETE, System.currentTimeMillis());
    }

    @Override
    public UserEventClass createUserEventClass(long userId, String eventClassName, long eventTime) throws SysException {
        UserEventClass userEventClass = new UserEventClass();
        if (eventTime >= System.currentTimeMillis()) {
            userEventClass.setClassType(UserEventClass.TYPE_ONLY_ONCE);
        } else {
            userEventClass.setClassType(UserEventClass.TYPE_EVERY_YEAR);
        }
        userEventClass.setBigDayFlag(UserEventClass.NOTICE_EVERY_100DAY_NOT);
        userEventClass.setName(eventClassName);
        userEventClass.setSourceType(UserEventClass.SOURCE_USER);
        userEventClass.setUserId(userId);

        userEventClassMapper.insert(userEventClass);

        return userEventClass;
    }

    @Override
    public void deleteUserEvent(UserEvent userEvent) {
        //设置状态
        userEventMapper.setStateByContact(userEvent.getId(), UserEvent.STATE_DELETE, System.currentTimeMillis());
        //清理最后一次
        if (userEvent.getContactId() != 0) {
            reCalculateContactNextEvent(userEvent.getContactId());
        }
        userEventNextTriggerMapper.deleteByEventId(userEvent.getId());
    }

    @Override
    public Map<Long, UserEvent> getUserEventByIds(List<Long> eventIds) {
        Map<Long, UserEvent> id2Map = new HashMap<>();
        if (CollectionUtils.isEmpty(eventIds)) {
            return id2Map;
        }
        List<UserEvent> eventList = userEventMapper.getListByIds(eventIds);
        if (!CollectionUtils.isEmpty(eventList)) {
            eventList.forEach(userEvent -> id2Map.put(userEvent.getId(), userEvent));
        }
        return id2Map;
    }

    @Override
    public Map<Long, ContactNextEvent> getContactNextEvent(List<Long> contactIds) {
        Map<Long, ContactNextEvent> contactId2EventMap = new HashMap<>();
        List<ContactNextEvent> nextContactEventList = contactNextEventMapper.getByContacts(contactIds);
        if (!CollectionUtils.isEmpty(nextContactEventList)) {
            nextContactEventList.forEach(contactNextEvent -> contactId2EventMap.put(contactNextEvent.getContactId(), contactNextEvent));
        }
        return contactId2EventMap;
    }

    @Override
    public ContactNextEvent calculateNextEventByEvent(UserEvent userEvent) {
        ContactNextEvent contactNextEvent = new ContactNextEvent();
        contactNextEvent.setTriggerTime(getNextTime(userEvent));
        contactNextEvent.setContactId(userEvent.getContactId());
        contactNextEvent.setEventId(userEvent.getId());
        contactNextEvent.setUpdateTime(System.currentTimeMillis());
        return contactNextEvent;
    }

    private long getNextTime(UserEvent userEvent) {
        long triggerTime = userEvent.getEventTime();

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);//当前时间
        int currentMonth = calendar.get(Calendar.MONTH);//当前时间
        int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);//当前时间
        long currentTimes = calendar.getTimeInMillis();

        calendar.setTimeInMillis(userEvent.getEventTime());
        int eventMonth = calendar.get(Calendar.MONTH);
        int eventDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        int nextTriggerYear = currentYear;
        int nextTriggerMonth = currentMonth;
        int nextTriggerDayOfMonth = eventDayOfMonth;

        //默认就发生一次
        UserEventClass userEventClass = userEvent.getUserEventClass();
        if (userEventClass != null && userEventClass.getClassType() != UserEventClass.TYPE_ONLY_ONCE) {
            if (userEventClass.getClassType() == UserEventClass.TYPE_EVERY_MONTH) {
                if (currentMonth == 11) {
                    if (eventDayOfMonth < currentDayOfMonth) {
                        nextTriggerYear = currentYear + 1;
                        nextTriggerMonth = 0;
                    }
                } else {
                    if (eventDayOfMonth < currentDayOfMonth) {
                        nextTriggerMonth = currentMonth + 1;
                    }
                }
                if (eventDayOfMonth == 31) {
                    if (TimeUtils.has30Day(nextTriggerMonth + 1)) {
                        nextTriggerDayOfMonth = 30;
                    } else if (nextTriggerMonth == 1) {
                        if (TimeUtils.isLeapYear(nextTriggerYear)) {
                            nextTriggerDayOfMonth = 29;
                        } else {
                            nextTriggerDayOfMonth = 28;
                        }
                    }
                } else if (eventDayOfMonth == 30) {
                    if (nextTriggerMonth == 1) {
                        if (TimeUtils.isLeapYear(nextTriggerYear)) {
                            nextTriggerDayOfMonth = 29;
                        } else {
                            nextTriggerDayOfMonth = 28;
                        }
                    }
                }
                calendar.set(nextTriggerYear, nextTriggerMonth, nextTriggerDayOfMonth);
                //提前几天提醒
                //TODO
                triggerTime = calendar.getTimeInMillis();
            } else if (userEventClass.getClassType() == UserEventClass.TYPE_EVERY_YEAR) {
                if (eventMonth < currentMonth || (eventMonth == currentMonth && eventDayOfMonth < currentDayOfMonth)) {
                    nextTriggerYear = currentYear + 1;
                }
                //闰年
                if (eventMonth == 1 && eventDayOfMonth == 29) {
                    nextTriggerDayOfMonth = 28;
                }
                calendar.set(nextTriggerYear, eventMonth, nextTriggerDayOfMonth);
                //逢百提醒
                int n = (int) ((calendar.getTimeInMillis() - currentTimes) / (24 * 60 * 60 * 1000));
                if (n > 100) {
                    int rollday = -1 * (n / 100) * 100;
                    calendar.add(Calendar.DAY_OF_YEAR, rollday);
                }
                triggerTime = calendar.getTimeInMillis();
            }
        }
        return triggerTime;
    }

    private UserEventNextTrigger calculateNextTrigger(UserEvent userEvent) {
        UserEventNextTrigger userEventNextTrigger = new UserEventNextTrigger();
        userEventNextTrigger.setEventId(userEvent.getId());
        userEventNextTrigger.setUpdateTime(System.currentTimeMillis());
        userEventNextTrigger.setUserId(userEvent.getUserId());

        userEventNextTrigger.setTriggerTime(getNextTime(userEvent));
        return userEventNextTrigger;
    }

    @Override
    public ContactNextEvent getNextEventByContact(long contactId) {
        List<ContactNextEvent> nextEventList = contactNextEventMapper.getByContacts(Arrays.asList(contactId));
        if (CollectionUtils.isEmpty(nextEventList)) {
            return null;
        }
        return nextEventList.get(0);
    }

    @Override
    public PageData<UserEventNextTrigger> getEventNextTriggerList(long userId, int page, int limit) {
        PageData<UserEventNextTrigger> pageData = new PageData<>();
        int total = userEventNextTriggerMapper.countByUser(userId);
        pageData.setTotal(total);
        int offet = (page - 1) * limit;
        if (total < offet) {
            return pageData;
        }
        List<UserEventNextTrigger> dataList = userEventNextTriggerMapper.getByUser(userId, offet, limit);
        pageData.setData(dataList);
        return pageData;
    }

    @Override
    public UserEventClass getEventClass(int eventClassId) {
        return userEventClassMapper.getById(eventClassId);
    }

}
