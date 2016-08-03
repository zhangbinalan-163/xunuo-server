package com.dabo.xunuo.service;

import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.entity.User;
import com.dabo.xunuo.entity.UserCertificate;

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

    /**
     * 新建一个用户
     * @param user
     * @param userCertificate
     * @throws SysException
     */
    void createUser(User user,UserCertificate userCertificate) throws SysException;
}
