package com.dabo.xunuo.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求解析的Filter
 * 解析一些HEADER,设置到当前请求环境中
 */
public class HeaderParseFilter extends BaseFilter {

    @Override
    protected boolean beforeExecute(HttpServletRequest request,HttpServletResponse response) throws Exception{

        return true;
    }
}
