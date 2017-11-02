package com.dabo.xunuo.base.dao;

import com.dabo.xunuo.base.entity.DeviceInfo;

import org.apache.ibatis.annotations.Param;


/**
 * 设备信息操作Mapper
 * Created by zhangbin on 16/7/31.
 */
public interface DeviceInfoMapper extends BaseMapper<Long, DeviceInfo> {

    /**
     * 获取用户的设备信息
     *
     * @param userId
     * @return
     */
    DeviceInfo getByUserId(@Param("userId") long userId);

    /**
     * 根据设备ID查询设备信息
     *
     * @param deviceId
     * @return
     */
    DeviceInfo getByDeviceId(@Param("deviceId") String deviceId, @Param("deviceType") int deviceType);

    /**
     * 设备关联登录用户
     *
     * @param deviceId
     * @param userId
     * @param loginTime
     */
    void bindUser(@Param("deviceId") String deviceId, @Param("deviceType") int deviceType, @Param("userId") long userId, @Param("loginTime") long loginTime);
}

