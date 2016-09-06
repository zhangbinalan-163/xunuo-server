package com.dabo.xunuo.util;

import com.dabo.xunuo.common.Constants;
import com.dabo.xunuo.common.exception.SysException;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求的工具类
 */
public class RequestUtils {

    /**
     * 从请求获取KEY的参数,如果为空抛出异常
     * @param paramMap
     * @param key
     * @return
     * @throws SysException
     */
    public static String getString(Map<String,String> paramMap,String key) throws SysException {
        String str = paramMap.get(key);
        if (StringUtils.isEmpty(str)) {
            throw new SysException("参数为空或格式错误:"+key,Constants.ERROR_CODE_INVALID_PARAM);
        }
        return str;
    }

    /**
     * 从请求获取KEY的参数,允许为空,如果为空返回默认值
     * @param paramMap
     * @param key
     * @param defau
     * @return
     */
    public static String getString(Map<String,String> paramMap,String key, String defau) {
        String str = paramMap.getOrDefault(key, defau);
        return str;
    }

    /**
     * 从请求获取KEY的参数,如果为空抛出异常
     * @param paramMap
     * @param key
     * @return
     * @throws SysException
     */
    public static int getInt(Map<String,String> paramMap,String key) throws SysException {
        String str = paramMap.get(key);
        try {
            return Integer.parseInt(str);
        }catch (Exception e){
            throw new SysException("参数为空或格式错误:"+key,Constants.ERROR_CODE_INVALID_PARAM);
        }
    }

    /**
     * 从请求获取KEY的参数,如果为空返回默认值
     * @param paramMap
     * @param key
     * @return
     * @throws SysException
     */
    public static int getInt(Map<String,String> paramMap,String key,int defau) throws SysException {
        String str = paramMap.get(key);
        try {
            return Integer.parseInt(str);
        }catch (Exception e){
            return defau;
        }
    }

    /**
     * 从请求获取KEY的int参数,并检查范围
     * @param paramMap
     * @param key
     * @return
     * @throws SysException
     */
    public static int getInt(Map<String,String> paramMap,String key,int min,int max) throws SysException {
        int value=getInt(paramMap, key);
        if(value<min||value>max){
            throw new SysException("参数为空或格式错误:"+key,Constants.ERROR_CODE_INVALID_PARAM);
        }
        return value;
    }
    /**
     * 从请求获取KEY的参数,如果为空抛出异常
     * @param paramMap
     * @param key
     * @return
     * @throws SysException
     */
    public static long getLong(Map<String,String> paramMap,String key) throws SysException {
        String str = paramMap.get(key);
        try {
            return Long.parseLong(str);
        }catch (Exception e){
            throw new SysException("参数为空或格式错误:"+key,Constants.ERROR_CODE_INVALID_PARAM);
        }
    }

    /**
     * 从请求获取KEY的参数
     * @param paramMap
     * @param key
     * @return
     * @throws SysException
     */
    public static long getLong(Map<String,String> paramMap,String key,long defau) throws SysException {
        String str = paramMap.get(key);
        try {
            return Long.parseLong(str);
        }catch (Exception e){
            return defau;
        }
    }

    /**
     * 获取手机号参数并校验
     * @param paramMap
     * @param key
     * @return
     * @throws SysException
     */
    public static String getMobileString(Map<String,String> paramMap,String key) throws SysException {
        String mobile=getString(paramMap, key);
        if(!StringUtils.isLegalMobile(mobile)){
            throw new SysException("手机号格式非法"+key,Constants.ERROR_CODE_INVALID_PARAM);
        }
        return mobile;
    }

    /**
     * 从请求获取KEY的日期类型参数,如果为空抛出异常
     * @param paramMap
     * @param key
     * @return
     * @throws SysException
     */
    public static Date getDate(Map<String,String> paramMap, String key ,String format) throws SysException {
        String str = paramMap.get(key);
        if (StringUtils.isEmpty(str)) {
            throw new SysException("参数为空或格式错误:"+key,Constants.ERROR_CODE_INVALID_PARAM);
        }
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(str);
        } catch (ParseException e) {
            throw new SysException("参数为空或格式错误:"+key,Constants.ERROR_CODE_INVALID_PARAM);
        }
    }
    /**
     * 获取请求的所有非空参数
     * @param request
     * @return
     */
    public static Map<String,String> getNotEmptyParam(HttpServletRequest request){
        Map<String,String> map = new HashMap();
        Enumeration paramNames = request.getParameterNames();

        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String value=request.getParameter(paramName);
            if(!StringUtils.isEmpty(value)){
                map.put(paramName,value);
            }
        }
        return map;
    }

    public static String getHeader(HttpServletRequest request,String headerName) throws SysException {
        String value=request.getHeader(headerName);
        if(StringUtils.isEmpty(value)){
            throw new SysException("HEADER为空或格式错误:"+headerName,Constants.ERROR_CODE_INVALID_PARAM);
        }
        return value;
    }

    public static String getHeader(HttpServletRequest request,String headerName,String defaultValue) throws SysException {
        String value=request.getHeader(headerName);
        if(StringUtils.isEmpty(value)){
            return defaultValue;
        }
        return value;
    }
}
