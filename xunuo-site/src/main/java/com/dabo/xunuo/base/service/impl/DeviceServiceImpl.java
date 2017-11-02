package com.dabo.xunuo.base.service.impl;

import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.dao.DeviceInfoMapper;
import com.dabo.xunuo.base.entity.DeviceInfo;
import com.dabo.xunuo.base.service.BaseSerivce;
import com.dabo.xunuo.base.service.IDeviceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangbin on 16/8/5.
 */
@Service
public class DeviceServiceImpl extends BaseSerivce implements IDeviceService {
    @Autowired
    private DeviceInfoMapper deviceInfoMapper;

    @Override
    public void createDevice(String deviceId, int deviceTypeId) throws SysException {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setDeviceId(deviceId);
        deviceInfo.setCreateTime(System.currentTimeMillis());
        deviceInfo.setDeviceTypeId(deviceTypeId);
        deviceInfoMapper.insert(deviceInfo);
    }

    @Override
    public DeviceInfo getByDeviceId(String deviceId, int deviceTypeId) throws SysException {
        return deviceInfoMapper.getByDeviceId(deviceId, deviceTypeId);
    }

    @Override
    public void userLogin(String deviceId, int deviceTypeId, long userId) throws SysException {
        deviceInfoMapper.bindUser(deviceId, deviceTypeId, userId, System.currentTimeMillis());
    }

    @Override
    public DeviceInfo getByUserId(long userId) {
        return deviceInfoMapper.getByUserId(userId);
    }
}
