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
     * @throws SysException
     */
    void createDevice(String deviceId) throws SysException;

    /**
     * 用户在设备上登录
     * @param deviceId
     * @param userId
     * @throws SysException
     */
    void userLogin(String deviceId,long userId) throws SysException;

    /**
     * 根据设备ID查询设备信息
     * @param deviceId
     * @return
     * @throws SysException
     */
    DeviceInfo getByDeviceId(String deviceId) throws SysException;


}
