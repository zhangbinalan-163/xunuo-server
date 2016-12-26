package com.dabo.xunuo.base.service.impl;

import com.dabo.xunuo.base.dao.UserCertificateMapper;
import com.dabo.xunuo.base.service.IUserService;
import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.dao.UserMapper;
import com.dabo.xunuo.base.entity.User;
import com.dabo.xunuo.base.entity.UserCertificate;
import com.dabo.xunuo.base.event.impl.UserRegEvent;
import com.dabo.xunuo.base.service.BaseSerivce;
import com.dabo.xunuo.base.util.StringUtils;
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
        user.setAccessToken("");
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

    @Override
    public User getByOpenId(String openId, int sourceType) throws SysException {
        return userMapper.getByOpenId(sourceType,openId);
    }

    @Override
    public long createUser(int sourceType, String openId,String accessToken) throws SysException {
        //新建用户
        User user=new User();
        user.setPhone("");
        user.setCreateTime(System.currentTimeMillis());
        user.setSource(sourceType);
        user.setBindOpenId(openId);
        user.setAccessToken(accessToken);
        userMapper.insert(user);

        return user.getId();
    }

    @Override
    public void updateAccessToken(String accessToken, long userId) throws SysException {
        userMapper.updateAccessToken(accessToken,userId);
    }
}
