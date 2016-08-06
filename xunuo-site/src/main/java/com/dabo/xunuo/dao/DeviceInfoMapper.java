package com.dabo.xunuo.dao;

import com.dabo.xunuo.entity.DeviceInfo;
import org.apache.ibatis.annotations.Param;


/**
 * 设备信息操作Mapper
 * Created by zhangbin on 16/7/31.
 */
public interface DeviceInfoMapper extends BaseMapper<Long,DeviceInfo>{
    /**
     * 根据设备ID查询设备信息
     * @param deviceId
     * @return
     */
    DeviceInfo getByDeviceId(@Param("deviceId") String deviceId);
}

