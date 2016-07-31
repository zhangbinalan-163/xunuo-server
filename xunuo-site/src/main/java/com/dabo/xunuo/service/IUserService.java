package com.dabo.xunuo.service;

import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.entity.User;

/**
 * 用户业务接口
 */
public interface IUserService {
    /**
     * 根据手机号查询出用户信息
     * @param phone
     * @return
     * @throws SysException
     */
    User getByPhone(String phone) throws SysException;
}
