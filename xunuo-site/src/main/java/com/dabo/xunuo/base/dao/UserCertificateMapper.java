package com.dabo.xunuo.base.dao;

import com.dabo.xunuo.base.entity.UserCertificate;
import org.apache.ibatis.annotations.Param;

/**
 * 用户凭证信息操作Mapper
 * Created by zhangbin on 16/7/31.
 */
public interface UserCertificateMapper extends BaseMapper<Long,UserCertificate>{
    /**
     * 根据手机号查询账号
     * @param userId
     * @return
     */
    UserCertificate getByUserId(@Param("userId") long userId);
}

