/**
 * Copyright (C) 2006-2017 Tuniu All rights reserved
 */
package com.dabo.xunuo.base.service.impl;


import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dabo.xunuo.app.web.vo.ClientType;
import com.dabo.xunuo.base.entity.UserEvent;
import com.dabo.xunuo.base.service.IYmengService;
import com.dabo.xunuo.base.util.TimeUtils;
import com.dabo.xunuo.youmeng.PushClient;
import com.dabo.xunuo.youmeng.ios.IOSUnicast;

@Service
public class YmengServiceImpl implements IYmengService {
    private Logger LOG = LoggerFactory.getLogger(YmengServiceImpl.class);

    private static String appkey = "586b383d310c936d9a0005e3";
    private static String appMasterSecret = "dbjpttl6cb7nfp59iclh1vuwvdyo3zey";
    private PushClient client;

    @PostConstruct
    public void init() {
        client = new PushClient();
    }

    @Override
    public void sendNotice(String deviceId, int deviceType, UserEvent userEvent) {
        try {
            if (deviceType == ClientType.IPHONE.getId()) {
                IOSUnicast unicast = new IOSUnicast(appkey, appMasterSecret);
                unicast.setDeviceToken(deviceId);
                unicast.setAlert("事件:" + userEvent.getName() + "还有" + TimeUtils.getRemainDays(userEvent.getNextEventTime()) + "天");
                unicast.setBadge(0);
                unicast.setSound("default");
                //TODO
                unicast.setTestMode();
                unicast.setCustomizedField("eventId", userEvent.getId() + "");
                client.send(unicast);
            }
        } catch (Exception e) {
            LOG.error("send notice by ymeng fail", e);
        }
    }

    @Override
    public void sendEventHappen(String deviceId, int deviceType, UserEvent userEvent) {
        try {
            if (deviceType == ClientType.IPHONE.getId()) {
                IOSUnicast unicast = new IOSUnicast(appkey, appMasterSecret);
                unicast.setDeviceToken(deviceId);
                unicast.setAlert("事件" + userEvent.getName() + "提醒");
                unicast.setBadge(0);
                unicast.setSound("default");
                //TODO
                unicast.setTestMode();
                unicast.setCustomizedField("eventId", userEvent.getId() + "");
                client.send(unicast);
            }
        } catch (Exception e) {
            LOG.error("send notice by ymeng fail", e);
        }
    }
}
