package com.dabo.xunuo.app.web.controller;


import com.dabo.xunuo.app.web.vo.ClientType;
import com.dabo.xunuo.base.entity.DeviceInfo;
import com.dabo.xunuo.base.service.IDeviceService;
import com.dabo.xunuo.app.web.vo.RequestContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 设备相关的controller
 */
@RequestMapping("/device")
@Controller
public class DeviceController extends BaseController {

    @Autowired
    private IDeviceService deviceService;

    /**
     * 注册设备ID
     * 主要检查ID是否已经存在,如果不存在要保存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reg")
    @ResponseBody
    public String regDeviceId() throws Exception {
        //参数的解析与校验
        String deviceId = RequestContext.getDeviceId();
        ClientType clientType = RequestContext.getClientType();
        //检查是否已经注册
        DeviceInfo deviceInfo = deviceService.getByDeviceId(deviceId, clientType.getId());
        if (deviceInfo == null) {
            //如果不存在,注册一下
            deviceService.createDevice(deviceId, clientType.getId());
        }
        return createDefaultSuccessResponse();
    }
}
