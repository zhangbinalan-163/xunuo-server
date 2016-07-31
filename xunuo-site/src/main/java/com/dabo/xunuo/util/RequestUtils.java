package com.dabo.xunuo.util;

import com.dabo.xunuo.common.Constants;
import com.dabo.xunuo.common.exception.SysException;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求的工具类
 */
public class RequestUtils {

    /**
     * 从请求获取KEY的参数,如果为空抛出异常
     * @param httpServletRequest
     * @param key
     * @return
     * @throws SysException
     */
    public static String getString(HttpServletRequest httpServletRequest,String key) throws SysException {
        String str = httpServletRequest.getParameter(key);
        if (StringUtils.isEmpty(str)) {
            throw new SysException("参数为空或格式错误:"+key,Constants.ERROR_CODE_INVALID_PARAM);
        }
        return str;
    }

    /**
     * 从请求获取KEY的参数,允许为空,如果为空返回默认值
     * @param httpServletRequest
     * @param key
     * @return
     * @throws SysException
     */
    public static String getString(HttpServletRequest httpServletRequest,String key, String defau) {
        String str = httpServletRequest.getParameter(key);
        if(StringUtils.isEmpty(str)) {
            return defau;
        }
        return str;
    }
}
