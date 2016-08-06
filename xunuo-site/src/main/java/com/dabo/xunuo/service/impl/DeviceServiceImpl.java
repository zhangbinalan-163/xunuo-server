package com.dabo.xunuo.service.impl;

import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.dao.DeviceInfoMapper;
import com.dabo.xunuo.entity.DeviceInfo;
import com.dabo.xunuo.service.BaseSerivce;
import com.dabo.xunuo.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Created by zhangbin on 16/8/5.
 */
@Service
public class DeviceServiceImpl extends BaseSerivce implements IDeviceService {

    @Autowired
    private DeviceInfoMapper deviceInfoMapper;

    @Override
    public void createDevice(DeviceInfo deviceInfo) throws SysException {
        deviceInfoMapper.insert(deviceInfo);
    }

    @Override
    public DeviceInfo getByDeviceId(String deviceId) throws SysException {
        return deviceInfoMapper.getByDeviceId(deviceId);
    }
}
