package com.dabo.xunuo.base.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.dao.ContactNextEventMapper;
import com.dabo.xunuo.base.dao.UserEventClassMapper;
import com.dabo.xunuo.base.dao.UserEventMapper;
import com.dabo.xunuo.base.dao.UserEventNextNoticeMapper;
import com.dabo.xunuo.base.entity.Contact;
import com.dabo.xunuo.base.entity.ContactNextEvent;
import com.dabo.xunuo.base.entity.PageData;
import com.dabo.xunuo.base.entity.UserEvent;
import com.dabo.xunuo.base.entity.UserEventClass;
import com.dabo.xunuo.base.entity.UserEventNextNotice;
import com.dabo.xunuo.base.service.IUserEventService;
import com.dabo.xunuo.base.util.PageUtils;
import com.dabo.xunuo.base.util.TimeUtils;

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
    private UserEventNextNoticeMapper userEventNextNoticeMapper;

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
        //计算下一次事件的时间
        long nextEventTime = getEventNextTime(userEvent);
        userEvent.setNextEventTime(nextEventTime);
        userEventMapper.insert(userEvent);
        //联系人下一次事件的时间
        updateContactNextEvent(userEvent.getContactId());
        //下一个提醒时间
        updateEventNoticeTime(userEvent);
    }

    //计算下一次提醒时间
    private void updateEventNoticeTime(UserEvent userEvent) {
        UserEventNextNotice userEventNextNotice = new UserEventNextNotice();
        userEventNextNotice.setEventId(userEvent.getId());
        userEventNextNotice.setUpdateTime(System.currentTimeMillis());
        userEventNextNotice.setUserId(userEvent.getUserId());
        //下一次提醒时间
        long nextNoticeTime = getEventNextNoticeTime(userEvent);
        userEventNextNotice.setTimeFlag(nextNoticeTime == userEvent.getNextEventTime() ? UserEventNextNotice.EVENT_TIME : UserEventNextNotice.NOTICE_TIME);
        userEventNextNotice.setNoticeTime(nextNoticeTime);

        userEventNextNoticeMapper.deleteByEventId(userEvent.getId());
        if (nextNoticeTime > System.currentTimeMillis()) {
            userEventNextNoticeMapper.insert(userEventNextNotice);
        }
    }


    private void updateContactNextEvent(long contactId) {
        if (contactId != 0) {
            //删除旧的
            ContactNextEvent oldNext = getNextEventByContact(contactId);
            if (oldNext != null) {
                contactNextEventMapper.delete(oldNext.getId());
            }
            //获取联系人的全部事件
            List<UserEvent> allEventList = userEventMapper.getAllEventByContact(contactId, UserEvent.STATE_NORMAL);
            if (!CollectionUtils.isEmpty(allEventList)) {
                List<UserEvent> eventList = new ArrayList<>();
                allEventList.forEach(userEvent -> {
                    if (userEvent.getNextEventTime() > System.currentTimeMillis()) {
                        eventList.add(userEvent);
                    }
                });
                if (eventList.isEmpty()) {
                    return;
                }
                eventList.sort((a, b) -> (int) (a.getNextEventTime() - b.getNextEventTime()));
                UserEvent event = eventList.get(0);

                ContactNextEvent contactNextEvent = new ContactNextEvent();
                contactNextEvent.setNextEventTime(event.getNextEventTime());
                contactNextEvent.setContactId(event.getContactId());
                contactNextEvent.setEventId(event.getId());
                contactNextEvent.setUpdateTime(System.currentTimeMillis());
                contactNextEventMapper.insert(contactNextEvent);
            }
        }
    }

    @Override
    public void updateUserEvent(UserEvent userEvent, UserEvent oldUserEvent) throws SysException {

        userEvent.setUpdateTime(System.currentTimeMillis());
        long nextEventTime = getEventNextTime(userEvent);
        userEvent.setNextEventTime(nextEventTime);
        userEventMapper.update(userEvent);

        //联系人下一次数据
        long contactId = userEvent.getContactId();
        long oldContactId = oldUserEvent.getContactId();
        if (oldContactId != 0) {
            updateContactNextEvent(oldContactId);
        }
        if (oldContactId != 0 && contactId != oldContactId) {
            updateContactNextEvent(oldContactId);
        }

        //下一个提醒时间
        updateEventNoticeTime(userEvent);
    }

    @Override
    public UserEvent getUserEventById(long eventId) throws SysException {
        return userEventMapper.getById(eventId);
    }

    @Override
    public void deleteUserEventByContactId(long contactId) throws SysException {

        List<UserEvent> allEventList = userEventMapper.getAllEventByContact(contactId, Contact.STATE_NORMAL);
        if (!CollectionUtils.isEmpty(allEventList)) {
            List<Long> userEventIds = new ArrayList<>();
            allEventList.forEach(userEvent -> userEventIds.add(userEvent.getId()));
            userEventNextNoticeMapper.deleteByEventIds(userEventIds);
            //设置事件状态
            userEventMapper.setState(userEventIds, UserEvent.STATE_DELETE, System.currentTimeMillis());
        }
        //删除下一个发生的事件
        contactNextEventMapper.deleteByContact(contactId);
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
        userEventMapper.setState(Arrays.asList(userEvent.getId()), UserEvent.STATE_DELETE, System.currentTimeMillis());
        //清理联系人的最后一次时间
        updateContactNextEvent(userEvent.getContactId());
        //清理下一次提醒时间
        userEventNextNoticeMapper.deleteByEventId(userEvent.getId());
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

    private long getEventNextNoticeTime(UserEvent userEvent) {
        long triggerTime = userEvent.getEventTime();

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);//当前时间
        int currentMonth = calendar.get(Calendar.MONTH);//当前时间
        int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);//当前时间
        long currentTimes = calendar.getTimeInMillis();

        calendar.setTimeInMillis(userEvent.getEventTime());
        calendar.set(Calendar.HOUR, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

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
                int remindUnit = userEvent.getRemindIntervalUnit();
                int remindInterval = userEvent.getRemindInterval();
                int totalDay = remindInterval;
                if (remindUnit == UserEvent.REMIND_INTERVAL_TYPE_WEEK) {
                    totalDay = remindInterval * 7;
                }
                if (calendar.getTimeInMillis() - currentTimes > totalDay * 24 * 60 * 60 * 1000) {
                    calendar.roll(Calendar.DAY_OF_YEAR, totalDay * -1);
                }
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

    @Override
    public ContactNextEvent getNextEventByContact(long contactId) {
        List<ContactNextEvent> nextEventList = contactNextEventMapper.getByContacts(Arrays.asList(contactId));
        if (CollectionUtils.isEmpty(nextEventList)) {
            return null;
        }
        return nextEventList.get(0);
    }

    @Override
    public UserEventClass getEventClass(int eventClassId) {
        return userEventClassMapper.getById(eventClassId);
    }

    @Override
    public List<UserEventNextNotice> getEventNextNoticeList(long startTime, long endTime) {
        return userEventNextNoticeMapper.getByNoticeTime(startTime, endTime);
    }

    private long getEventNextTime(UserEvent userEvent) {
        long triggerTime = userEvent.getEventTime();

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);//当前时间
        int currentMonth = calendar.get(Calendar.MONTH);//当前时间
        int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);//当前时间

        calendar.setTimeInMillis(userEvent.getEventTime());
        calendar.set(Calendar.HOUR, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

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
                triggerTime = calendar.getTimeInMillis();
            }
        }
        return triggerTime;
    }

    @Override
    public PageData<UserEvent> getUserEventByUser(long userId, int page, int limit) throws SysException {
        long currentTime = TimeUtils.todayTime();
        PageData<UserEvent> pageData = new PageData<>();
        List<UserEvent> allUserEvent = userEventMapper.getAllEventByUser(userId, UserEvent.STATE_NORMAL);
        //全部事件
        if (!CollectionUtils.isEmpty(allUserEvent)) {
            pageData.setTotal(allUserEvent.size());

            List<UserEvent> allEventListInSort = new ArrayList<>();
            List<UserEvent> happendEventList = new ArrayList<>();
            List<UserEvent> notHappendEventList = new ArrayList<>();
            //只发生一次且已经发生
            allUserEvent.forEach(userEvent -> {
                if (userEvent.getNextEventTime() < currentTime) {
                    happendEventList.add(userEvent);
                } else {
                    notHappendEventList.add(userEvent);
                }
            });
            happendEventList.sort((a, b) ->{
                if(a.getNextEventTime()>b.getNextEventTime()){
                    return -1;
                }else if(a.getNextEventTime()<b.getNextEventTime()){
                    return 1;
                }else{
                    return 0;
                }
            });

            notHappendEventList.sort((a, b) ->{
                if(a.getNextEventTime()>b.getNextEventTime()){
                    return 1;
                }else if(a.getNextEventTime()<b.getNextEventTime()){
                    return -1;
                }else{
                    return 0;
                }
            });

            allEventListInSort.addAll(notHappendEventList);
            allEventListInSort.addAll(happendEventList);
            //page
            List<UserEvent> pageList = PageUtils.pageList(allEventListInSort, page, limit);
            pageData.setData(pageList);
        }
        return pageData;
    }

    @Override
    public List<UserEvent> getAllUserEvent() throws SysException {
        return userEventMapper.getAllEvent(UserEvent.STATE_NORMAL);
    }

    @Override
    public void updateEventNextTime(UserEvent userEvent) {
        //计算下一次事件的时间
        long nextEventTime = getEventNextTime(userEvent);
        userEventMapper.setNextEventTime(userEvent.getId(), nextEventTime, System.currentTimeMillis());
    }

    @Override
    public void updateContactNextTime(long contactId) {
        updateContactNextEvent(contactId);
    }

    @Override
    public void updateEventNextNoticeTime(UserEvent userEvent) {
        updateEventNoticeTime(userEvent);
    }

    @Override
    public PageData<UserEvent> getUserEventByContact(long contactId, int page, int limit) throws SysException {
        long currentTime = TimeUtils.todayTime();
        PageData<UserEvent> pageData = new PageData<>();
        List<UserEvent> allUserEvent = userEventMapper.getAllEventByContact(contactId, UserEvent.STATE_NORMAL);
        //全部事件
        if (!CollectionUtils.isEmpty(allUserEvent)) {
            pageData.setTotal(allUserEvent.size());

            List<UserEvent> allEventListInSort = new ArrayList<>();
            List<UserEvent> happendEventList = new ArrayList<>();
            List<UserEvent> notHappendEventList = new ArrayList<>();
            //只发生一次且已经发生
            allUserEvent.forEach(userEvent -> {
                if (userEvent.getNextEventTime() < currentTime) {
                    happendEventList.add(userEvent);
                } else {
                    notHappendEventList.add(userEvent);
                }
            });
            happendEventList.sort((a, b) ->{
                if(a.getNextEventTime()>b.getNextEventTime()){
                    return -1;
                }else if(a.getNextEventTime()<b.getNextEventTime()){
                    return 1;
                }else{
                    return 0;
                }
            });

            notHappendEventList.sort((a, b) ->{
                if(a.getNextEventTime()>b.getNextEventTime()){
                    return 1;
                }else if(a.getNextEventTime()<b.getNextEventTime()){
                    return -1;
                }else{
                    return 0;
                }
            });

            allEventListInSort.addAll(notHappendEventList);
            allEventListInSort.addAll(happendEventList);
            //page
            List<UserEvent> pageList = PageUtils.pageList(allEventListInSort, page, limit);
            pageData.setData(pageList);
        }
        return pageData;
    }

}
