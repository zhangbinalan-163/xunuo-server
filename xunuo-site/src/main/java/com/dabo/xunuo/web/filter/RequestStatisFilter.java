package com.dabo.xunuo.web.filter;

import com.dabo.xunuo.util.StringUtils;
import com.dabo.xunuo.web.vo.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统计请求的Filter
 * 记录请求的路径以及请求处理时间、处理结果
 * Created by zhangbin2 on 15/10/16.
 */
public class RequestStatisFilter extends BaseFilter {
    private static Logger LOGGER= LoggerFactory.getLogger(RequestStatisFilter.class);

    @Override
    protected boolean beforeExecute(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //记录开始时间
        RequestContext.start();
        //生成请求traceId,目前就是产生一个uuid
        RequestContext.traceId(StringUtils.uuid());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        boolean isSuccess=true;
        if(ex!=null||RequestContext.getException()!=null){
            isSuccess=false;
        }
        LOGGER.info("request uri={},time(ms)={},success={},traceId={}",request.getRequestURI(),RequestContext.getProcessTime(),isSuccess,RequestContext.getTraceId());
    }
}
