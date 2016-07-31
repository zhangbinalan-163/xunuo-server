package com.dabo.xunuo.dao;

import com.dabo.xunuo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;

/**
 * 用户信息操作Mapper
 * Created by zhangbin on 16/7/31.
 */
public interface UserMapper extends BaseMapper<Long,User>{
    /**
     * 根据手机号查询账号
     * @param phone
     * @return
     * @throws SQLException
     */
    User getByPhone(@Param("phone")String phone);
}

