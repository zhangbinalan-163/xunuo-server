package com.dabo.xunuo.service.impl;

import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.dao.UserEventMapper;
import com.dabo.xunuo.entity.PageData;
import com.dabo.xunuo.entity.RowBounds;
import com.dabo.xunuo.entity.UserEvent;
import com.dabo.xunuo.service.IUserEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        List<UserEvent> eventList = userEventMapper.getEventByUser(userId, todayDate, rowBounds);
        pageData.setTotal(total);
        pageData.setData(eventList);
        //计算不应该交给数据库去进行,数据量不大的时候可以把数据一次性取到内存中计算
        //1:先获取用户的全部事件ID、事件的event_time,后面可以把这个人的所有ID+event_time缓存起来
        //2:再对1中获取的信息根据event_time与当前时间的间隔大小 由小到大排序
        //3:对2中排好序的内容进行分页,拿到了limit个ID
        //4:根据ID批量查询出事件内容
        return pageData;
    }
}
