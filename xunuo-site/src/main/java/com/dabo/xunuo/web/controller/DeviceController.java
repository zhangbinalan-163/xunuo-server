package com.dabo.xunuo.web.controller;


import com.dabo.xunuo.common.Constants;
import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.entity.DeviceInfo;
import com.dabo.xunuo.service.IDeviceService;
import com.dabo.xunuo.web.vo.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 设备相关的controller
 */
@RequestMapping("/device")
@Controller
public class DeviceController extends BaseController{

    @Autowired
    private IDeviceService deviceService;

    /**
     * 注册设备ID
     * 主要检查ID是否已经存在,如果不存在要保存
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reg")
    @ResponseBody
    public String regDeviceId() throws Exception {
        //参数的解析与校验
        String deviceId=RequestContext.getDeviceId();
        //检查是否已经注册
        DeviceInfo deviceInfo = deviceService.getByDeviceId(deviceId);
        if(deviceInfo!=null){
            throw new SysException("该设备号已经存在", Constants.ERROR_CODE_DEVICE_EXSIST);
        }
        //如果不存在,注册一下
        deviceService.createDevice(deviceId);
        //
        return createDefaultSuccessResponse();
    }
}
