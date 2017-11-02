/**
 * Copyright (C) 2006-2017 Tuniu All rights reserved
 */
package com.dabo.xunuo.base.service;

import com.dabo.xunuo.base.entity.UserEvent;

public interface IYmengService {

    void sendNotice(String deviceId, int deviceType, UserEvent userEvent);

    void sendEventHappen(String deviceId, int deviceType, UserEvent userEvent);
}
