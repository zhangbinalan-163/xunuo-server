package com.dabo.xunuo.service.impl;

import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.dao.UserMapper;
import com.dabo.xunuo.entity.User;
import com.dabo.xunuo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户业务实现
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getByPhone(String phone) throws SysException {
        try {
            return userMapper.getByPhone(phone);
        }catch (Exception e){
            throw new SysException("查询用户信息失败",e);
        }
    }
}
