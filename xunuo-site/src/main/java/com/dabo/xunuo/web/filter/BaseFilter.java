package com.dabo.xunuo.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 基础的Filter,封装了一些通用功能
 *
 * Created by zhangbin on 15/10/16.
 */
public abstract class BaseFilter extends HandlerInterceptorAdapter {
    private static Logger LOGGER = LoggerFactory.getLogger(BaseFilter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{
            return beforeExecute(request,response);
        }catch (Exception e){
            //异常处理,发生异常不执行逻辑
            processException(request,response,e);
            return false;
        }
    }

    /**
     * 执行请求之前需要首先执行的逻辑
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    protected abstract boolean beforeExecute(HttpServletRequest request,HttpServletResponse response) throws Exception;

    /**
     * 异常信息的处理
     * @param exception
     */
    protected void processException(HttpServletRequest request,HttpServletResponse response,Exception exception) throws IOException {
        //记录当前异常信息
        //打印日志
        String uri=request.getRequestURI();
        LOGGER.error("[{}]request fail",uri,exception);
        //处理异常信息，并输出
    }
}
