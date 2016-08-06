package com.dabo.xunuo.service;

import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.entity.DeviceInfo;
import com.dabo.xunuo.entity.User;
import com.dabo.xunuo.entity.UserCertificate;

/**
 * 设备相关业务接口
 */
public interface IDeviceService {

    /**
     * 新建设备信息
     * @param deviceInfo
     * @throws SysException
     */
    void createDevice(DeviceInfo deviceInfo) throws SysException;

    /**
     * 根据设备ID查询设备信息
     * @param deviceId
     * @return
     * @throws SysException
     */
    DeviceInfo getByDeviceId(String deviceId) throws SysException;
}
