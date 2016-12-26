package com.dabo.xunuo.base.util;

import com.dabo.xunuo.base.common.Constants;
import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.entity.DataResponse;
import com.dabo.xunuo.app.web.vo.RequestContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 响应工具类
 */
public class ResponseUtils {

    /**
     * 处理异常信息，并输出response
     *
     * @param httpResponse
     * @throws IOException
     */
    public static void errorResponse( HttpServletResponse httpResponse) throws IOException {
        //组装异常信息
        DataResponse responseMessage = generateExceptionMessage();
        //输出异常信息
        writeResponse(httpResponse, responseMessage);
    }

    /**
     * 组装异常信息
     * @return
     */
    private static DataResponse generateExceptionMessage() {
        String traceId=RequestContext.getTraceId();
        Exception exception = RequestContext.getException();

        int code = Constants.DEFAULT_CODE_ERROR;
        String message = exception.getMessage();
        if (exception instanceof SysException) {
            SysException sysException = (SysException) exception;
            code = sysException.getErrorCode();
            message = sysException.getMessage();
        } else {
            Throwable t = exception.getCause();
            if (t instanceof SysException) {
                SysException sysException = (SysException) t;
                code = sysException.getErrorCode();
                message = sysException.getMessage();
            }
        }
        return createFailResponse(code, message,traceId);
    }

    /**
     * 输出响应信息
     *
     * @param responseVO
     */
    public static void writeResponse(HttpServletResponse httpResponse, DataResponse responseVO) throws IOException {
        String jsonMessage = JsonUtils.fromObject(responseVO);

        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.getWriter().print(jsonMessage);
    }

    /**
     * 生成错误信息
     *
     * @param code
     * @param message
     * @return
     */
    private static DataResponse createFailResponse(int code, String message,String traceId) {
        DataResponse dataResponse = new DataResponse();
        dataResponse.setErrorCode(code);
        dataResponse.setErrMsg(message);
        dataResponse.setTraceId(traceId);
        return dataResponse;
    }
}
