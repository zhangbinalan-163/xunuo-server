package com.dabo.xunuo.service;

import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.entity.User;
import com.dabo.xunuo.entity.UserCertificate;

/**
 * 用户业务接口
 */
public interface IUserService {
    /**
     * 获取用户信息
     * @param userId
     * @return
     * @throws SysException
     */
    User getByUserId(long userId) throws SysException;

    /**
     * 根据手机号查询出用户信息
     * @param phone
     * @return
     * @throws SysException
     */
    User getByPhone(String phone) throws SysException;

    /**
     * 新建一个用户
     * @param phone
     * @param password
     * @throws SysException
     */
    long createUser(String phone,String password) throws SysException;

    /**
     * 获取用户密码信息
     * @param user
     * @return
     * @throws SysException
     */
    UserCertificate getUserCertificate(User user) throws SysException;

    /**
     * 修改密码
     * @param user
     * @param password
     * @throws SysException
     */
    void resetPassword(User user, String password) throws SysException;
}
