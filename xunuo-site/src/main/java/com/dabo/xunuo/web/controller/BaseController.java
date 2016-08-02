package com.dabo.xunuo.web.controller;

import com.dabo.xunuo.common.Constants;
import com.dabo.xunuo.dao.DataResponse;
import com.dabo.xunuo.util.JsonUtils;
import com.dabo.xunuo.util.ResponseUtils;
import com.dabo.xunuo.web.vo.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * 基本的处理器，封装一些通用功能
 * Date: 2015-11-09
 *
 * @author: fanweilian
 */
public abstract class BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    /**
     * 自定义异常统一处理
     *
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    protected void handleException(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Exception exception) {
        //记录当前异常信息
        RequestContext.exception(exception);

        String traceId = RequestContext.getTraceId();
        String uri = httpRequest.getRequestURI();
        LOGGER.error("[{}]request fail,traceId:{}", uri,traceId, exception);

        //处理异常信息，并输出
        try {
            ResponseUtils.errorResponse(httpResponse);
        } catch (IOException e) {
            LOGGER.error("response fail", e);
        }
    }

    /**
     * 产生成功的响应信息
     * @param data
     * @return
     */
    protected String createSuccessResponse(Object data){
        DataResponse dataResponse=new DataResponse();
        dataResponse.setTraceId(RequestContext.getTraceId());
        dataResponse.setData(data);
        dataResponse.setErrMsg("success");
        dataResponse.setErrorCode(Constants.DEFAULT_CODE_SUCCESS);
        return JsonUtils.fromObject(dataResponse);
    }
}
