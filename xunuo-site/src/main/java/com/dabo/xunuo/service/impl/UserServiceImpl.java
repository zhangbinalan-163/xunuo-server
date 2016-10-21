package com.dabo.xunuo.service.impl;

import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.dao.UserCertificateMapper;
import com.dabo.xunuo.dao.UserMapper;
import com.dabo.xunuo.entity.User;
import com.dabo.xunuo.entity.UserCertificate;
import com.dabo.xunuo.event.impl.UserRegEvent;
import com.dabo.xunuo.service.BaseSerivce;
import com.dabo.xunuo.service.IUserService;
import com.dabo.xunuo.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户业务实现
 */
@Service
public class UserServiceImpl extends BaseSerivce implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserCertificateMapper userCertificateMapper;

    @Override
    public User getByPhone(String phone) throws SysException {
        return userMapper.getByPhone(phone);
    }

    @Override
    public User getByUserId(long userId) throws SysException {
        return userMapper.getById(userId);
    }

    @Override
    public long createUser(String phone,String password) throws SysException {
        //新建用户
        User user=new User();
        user.setPhone(phone);
        user.setCreateTime(System.currentTimeMillis());
        user.setSource(User.SOURCE_OUR);
        user.setBindOpenId("");
        user.setPhone(phone);
        userMapper.insert(user);
        //保存用户密码
        String salt=StringUtils.genCode();
        UserCertificate userCertificate=new UserCertificate();
        userCertificate.setPassword(StringUtils.md5(password+"#"+salt));
        userCertificate.setSalt(salt);
        userCertificate.setUserId(user.getId());
        userCertificate.setUpdateTime(System.currentTimeMillis());
        userCertificateMapper.insert(userCertificate);
        //发布用户注册事件
        UserRegEvent userRegEvent=new UserRegEvent(user);
        eventBus.post(userRegEvent);

        return user.getId();
    }

    @Override
    public UserCertificate getUserCertificate(User user) throws SysException {
        return userCertificateMapper.getByUserId(user.getId());
    }

    @Override
    public void resetPassword(User user, String password) throws SysException {
        UserCertificate certificate = getUserCertificate(user);
        if(certificate!=null){
            String salt=StringUtils.genCode();
            certificate.setPassword(StringUtils.md5(password+"#"+salt));
            certificate.setSalt(salt);
            certificate.setUpdateTime(System.currentTimeMillis());
            userCertificateMapper.update(certificate);
        }else{
            String salt=StringUtils.genCode();
            UserCertificate userCertificate=new UserCertificate();
            userCertificate.setPassword(StringUtils.md5(password+"#"+salt));
            userCertificate.setSalt(salt);
            userCertificate.setUserId(user.getId());
            userCertificate.setUpdateTime(System.currentTimeMillis());
            userCertificateMapper.insert(userCertificate);
        }
    }
}
