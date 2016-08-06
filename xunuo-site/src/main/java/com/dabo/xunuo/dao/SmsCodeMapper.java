package com.dabo.xunuo.dao;

import com.dabo.xunuo.entity.SmsCode;
import org.apache.ibatis.annotations.Param;


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
}

