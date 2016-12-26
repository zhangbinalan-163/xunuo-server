package com.dabo.xunuo.base.dao;

import com.dabo.xunuo.base.entity.SmsCode;

import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 验证码信息Mapper
 * Created by zhangbin on 16/7/31.
 */
public interface SmsCodeMapper extends BaseMapper<Long,SmsCode>{
    /**
     * 获取指定类型的手机号当前的验证码
     * @param type
     * @param mobile
     * @return
     */
    SmsCode getByMobile(@Param("smsType")int type,@Param("mobile")String mobile);

    /**
     * 获取create_time+validInterval<validEndTime的验证码ID
     * @param validEndTime
     * @return
     */
    List<Long> getByValidTime(@Param("validEndTime")long validEndTime);
}

