package com.dabo.xunuo.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限过滤器
 * 对是否登录等进行检查
 */
public class RightFilter extends BaseFilter {
    private static Logger LOGGER = LoggerFactory.getLogger(RightFilter.class);

    @Override
    protected boolean beforeExecute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //做一些权限的检查
        //是否登录/权限
        return true;
    }
}
