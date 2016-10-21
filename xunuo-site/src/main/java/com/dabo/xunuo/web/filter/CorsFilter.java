package com.dabo.xunuo.web.filter;

import com.dabo.xunuo.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 跨域请求头过滤器
 */
public class CorsFilter extends BaseFilter {

    @Override
    protected boolean beforeExecute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String originHeader=request.getHeader("Origin");
        if(!StringUtils.isEmpty(originHeader) && permit(originHeader)) {
            response.setHeader("Access-Control-Allow-Origin", originHeader);
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        }
        return true;
    }

    private boolean permit(String originHeader) {
        return true;
    }
}
