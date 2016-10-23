package com.dabo.xunuo.service.impl;

import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.dao.UserEventMapper;
import com.dabo.xunuo.dao.UserEventTypeMapper;
import com.dabo.xunuo.entity.PageData;
import com.dabo.xunuo.entity.RowBounds;
import com.dabo.xunuo.entity.UserEvent;
import com.dabo.xunuo.entity.UserEventType;
import com.dabo.xunuo.service.IUserEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 用户事件业务基础实现
 * Created by zhangbin on 16/8/28.
 */
@Service
public class UserEventServiceImpl implements IUserEventService{
    @Autowired
    private UserEventMapper userEventMapper;

    @Autowired
    private UserEventTypeMapper userEventTypeMapper;

    @Override
    public PageData<UserEvent> getUserEvent(long userId, int page, int limit) throws SysException {
        PageData<UserEvent> pageData=new PageData<>();
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        long todayDate = calendar.getTimeInMillis();

        int total=userEventMapper.countEventByUser(userId,todayDate);

        RowBounds rowBounds=new RowBounds(page,limit);

        List<UserEvent> eventList = userEventMapper.getEventByUser(userId, todayDate, "event_time", "asc" , rowBounds);
        pageData.setTotal(total);
        pageData.setData(eventList);
        //计算不应该交给数据库去进行,数据量不大的时候可以把数据一次性取到内存中计算
        //1:先获取用户的全部事件ID、事件的event_time,后面可以把这个人的所有ID+event_time缓存起来
        //2:再对1中获取的信息根据event_time与当前时间的间隔大小 由小到大排序
        //3:对2中排好序的内容进行分页,拿到了limit个ID
        //4:根据ID批量查询出事件内容
        return pageData;
    }

    @Override
    public List<UserEventType> getUserEventType(long userId) throws SysException {
        //首先获取系统指定的全部的事件类型
        List<UserEventType> systemEventTypeList = userEventTypeMapper.getEventTypeByUser(0, UserEventType.SOURCE_SYSTEM, "sort_index", "asc", null);
        //获取用户指定的全部的事件类型
        List<UserEventType> userEventTypeList = userEventTypeMapper.getEventTypeByUser(userId, UserEventType.SOURCE_USER, "sort_index", "asc", null);

        List<UserEventType> resultList=new ArrayList<>();
        if(!CollectionUtils.isEmpty(systemEventTypeList)){
            resultList.addAll(systemEventTypeList);
        }

        if(!CollectionUtils.isEmpty(userEventTypeList)){
            resultList.addAll(userEventTypeList);
        }
        return resultList;
    }

    @Override
    public void createUserEventType(UserEventType userEventType) throws SysException {
        //TODO 增加用户最多自定义类型数量
        long userId=userEventType.getUserId();
        int sourceType=userEventType.getSourceType();
        userEventType.setCreateTime(System.currentTimeMillis());
        userEventType.setUpdateTime(userEventType.getCreateTime());

        if(userEventType.getSortIndex() == 0){
            int count = userEventTypeMapper.countByUser(userId, sourceType);
            int maxSortIndex=0;
            if(count > 0){
                //如果没有指定排序顺序,找到当前最大的排序值+1,高并发会有并发问题,不过暂时不考虑
                maxSortIndex = userEventTypeMapper.getMaxSortIndex(userId,sourceType);
            }
            userEventType.setSortIndex(maxSortIndex+1);
            //insert
            userEventTypeMapper.insert(userEventType);
        }
        //TODO 其他情况
    }

    @Override
    public void createUserEvent(UserEvent userEvent) throws SysException {
        long currentTime=System.currentTimeMillis();
        userEvent.setCreateTime(currentTime);
        userEvent.setUpdateTime(currentTime);

        userEventMapper.insert(userEvent);
    }

    @Override
    public UserEventType getUserEventTypeById(long userEventTypeId) throws SysException {
        return userEventTypeMapper.getById(userEventTypeId);
    }

    @Override
    public void deleteUserEventType(UserEventType userEventType) throws SysException {
        userEventTypeMapper.delete(userEventType.getId());
    }

    @Override
    public void updateUserEvent(UserEvent userEvent) throws SysException {
        userEvent.setUpdateTime(System.currentTimeMillis());

        userEventMapper.update(userEvent);
    }

    @Override
    public UserEvent getUserEventById(long eventId) throws SysException {
        return userEventMapper.getById(eventId);
    }

    @Override
    public long countByEventType(long eventTypeId) throws SysException {
        return userEventMapper.countByType(eventTypeId);
    }

    @Override
    public void deleteUserEventByContactId(long contactId) throws SysException {
        userEventMapper.deleteByContactId(contactId);
    }

    @Override
    public UserEvent getNeariestEventByContact(long contactId) throws SysException {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        long todayDate = calendar.getTimeInMillis();

        RowBounds rowBounds=new RowBounds(1,1);

        List<UserEvent> eventList = userEventMapper.getEventByContact(contactId, todayDate, "event_time", "asc" , rowBounds);
        if(!CollectionUtils.isEmpty(eventList)){
            return eventList.get(0);
        }
        return null;
    }
}
