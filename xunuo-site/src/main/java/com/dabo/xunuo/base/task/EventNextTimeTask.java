package com.dabo.xunuo.base.task;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.entity.Contact;
import com.dabo.xunuo.base.entity.DeviceInfo;
import com.dabo.xunuo.base.entity.UserEvent;
import com.dabo.xunuo.base.entity.UserEventNextNotice;
import com.dabo.xunuo.base.service.IContactService;
import com.dabo.xunuo.base.service.IDeviceService;
import com.dabo.xunuo.base.service.IUserEventService;
import com.dabo.xunuo.base.service.IYmengService;

/**
 * 更新若干时间
 * Created by zhangbin on 16/9/3.
 */
@Component
public class EventNextTimeTask implements ITask {

    @Autowired
    private IUserEventService userEventService;

    @Autowired
    private IContactService contactService;

    @Override
    public void run() throws SysException {
        //获取全部事件,计算下一次发生时间
        List<UserEvent> allEventList = userEventService.getAllUserEvent();
        if (!CollectionUtils.isEmpty(allEventList)) {
            allEventList.forEach(userEvent -> {
                userEvent.getTimeDesc();
                //计算下一次触发时间
                userEventService.updateEventNextTime(userEvent);
                //计算下一次提醒时间
                userEventService.updateEventNextNoticeTime(userEvent);

            });
        }
        //获取全部联系人
        List<Contact> contactList = contactService.getAllContactList(Contact.STATE_NORMAL);
        if (!CollectionUtils.isEmpty(contactList)) {
            //计算联系人的下一个事件信息
            contactList.forEach(contact -> {
                userEventService.updateContactNextTime(contact.getId());
            });
        }
    }
}
