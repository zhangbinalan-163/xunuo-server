package com.dabo.xunuo.base.task;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.dabo.xunuo.app.web.vo.ClientType;
import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.entity.DeviceInfo;
import com.dabo.xunuo.base.entity.UserEvent;
import com.dabo.xunuo.base.entity.UserEventNextNotice;
import com.dabo.xunuo.base.service.IDeviceService;
import com.dabo.xunuo.base.service.IUserEventService;
import com.dabo.xunuo.base.service.IYmengService;
import com.dabo.xunuo.youmeng.ios.IOSUnicast;

/**
 * 当天事件触发任务
 * Created by zhangbin on 16/9/3.
 */
@Component
public class EventNoticeTask implements ITask {
    private static Logger LOG = LoggerFactory.getLogger(EventNoticeTask.class);

    @Autowired
    private IUserEventService userEventService;

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private IYmengService ymengService;

    @Override
    public void run() throws SysException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long startTime = calendar.getTimeInMillis();

        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        long endTime = calendar.getTimeInMillis();

        List<UserEventNextNotice> nextTriggerList = userEventService.getEventNextNoticeList(startTime, endTime);
        if (!CollectionUtils.isEmpty(nextTriggerList)) {
            nextTriggerList.forEach(userEventNextTrigger -> processEventTrigger(userEventNextTrigger));
        }

    }

    private void processEventTrigger(UserEventNextNotice userEventNextNotice) {
        long eventId = userEventNextNotice.getEventId();
        try {
            UserEvent userEvent = userEventService.getUserEventById(eventId);
            //通知
            sendNotice(userEventNextNotice, userEvent);
        } catch (Exception e) {
            LOG.error("process event trigger fail,eventId={}", eventId, e);
        }
    }

    private void sendNotice(UserEventNextNotice userEventNextNotice, UserEvent userEvent) {
        DeviceInfo deviceInfo = deviceService.getByUserId(userEvent.getUserId());
        if (deviceInfo != null) {
            if (userEventNextNotice.getTimeFlag() == UserEventNextNotice.NOTICE_TIME) {
                ymengService.sendNotice(deviceInfo.getDeviceId(), deviceInfo.getDeviceTypeId(), userEvent);
            } else {
                ymengService.sendEventHappen(deviceInfo.getDeviceId(), deviceInfo.getDeviceTypeId(), userEvent);
            }
        }
    }
}
