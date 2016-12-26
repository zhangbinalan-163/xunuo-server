package com.dabo.xunuo.base.dao;

import com.dabo.xunuo.base.entity.User;

import org.apache.ibatis.annotations.Param;


/**
 * 用户信息操作Mapper
 * Created by zhangbin on 16/7/31.
 */
public interface UserMapper extends BaseMapper<Long,User>{
    /**
     * 根据手机号获取用户信息
     * @param phone
     * @return
     */
    User getByPhone(@Param("phone")String phone);

    /**
     * 根据openId获取用户信息
     * @param source
     * @param openId
     * @return
     */
    User getByOpenId(@Param("source")int source,@Param("openId")String openId);

    /**
     * 修改accessToken
     * @param accessToken
     * @param userId
     */
    void updateAccessToken(@Param("accessToken")String accessToken,@Param("userId")long userId);
}

