package com.dabo.xunuo.web.vo;

import java.util.Map;

/**
 * 当前请求的上下文
 * 包括当前请求的开始执行时间、异常
 */
public class RequestContext {
    //请求的开始处理时间
    private static ThreadLocal<Long> startTimeThreadLocal=new ThreadLocal<>();
    //当前异常
    private static ThreadLocal<Exception> exceptionThreadLocal=new ThreadLocal<>();
    //当前请求的traceId
    private static ThreadLocal<String> traceIdThreadLocal=new ThreadLocal<>();
    //当前请求的非空参数
    private static ThreadLocal<Map<String,String> > webParamThreadLocal=new ThreadLocal<>();
    //当前请求的userId
    private static ThreadLocal<Long> userIdThreadLocal=new ThreadLocal<>();
    //当前请求的clientType代码
    private static ThreadLocal<ClientType> clientTypeThreadLocal=new ThreadLocal<>();
    //当前请求的APP版本号
    private static ThreadLocal<Version> versionThreadLocal=new ThreadLocal<>();
    //当前请求SID
    private static ThreadLocal<String> sidThreadLocal=new ThreadLocal<>();
    //当前请求的设备ID
    private static ThreadLocal<String> deviceIdThreadLocal=new ThreadLocal<>();
    /**
     * 记录开始处理请求时间
     */
    public static void start(){
        startTimeThreadLocal.set(System.currentTimeMillis());
    }

    /**
     * 获得请求的处理时间
     * @return
     */
    public static Long getProcessTime(){
        return System.currentTimeMillis()-startTimeThreadLocal.get();
    }

    /**
     * 记录当前异常
     * @param exception
     */
    public static void exception(Exception exception){
        exceptionThreadLocal.set(exception);
    }

    /**
     * 获得当前的异常
     * @return
     */
    public static Exception getException(){
        return exceptionThreadLocal.get();
    }

    /**
     * 设置当前请求的traceId
     * @param traceId
     */
    public static void traceId(String traceId){
        traceIdThreadLocal.set(traceId);
    }

    /**
     * 获取当前请求的traceId
     * @return
     */
    public static String getTraceId(){
        return traceIdThreadLocal.get();
    }

    /**
     * 获得当前请求的非空参数
     * @return
     */
    public static Map<String,String> getNotEmptyParamMap(){
        return webParamThreadLocal.get();
    }

    /**
     * 设置当前请求的参数
     * @param webParamMap
     */
    public static void webParamThreadLocal(Map<String, String> webParamMap) {
        webParamThreadLocal.set(webParamMap);
    }

    public static String getDeviceId() {
        return deviceIdThreadLocal.get();
    }

    public static void setDeviceId(String deviceId) {
        deviceIdThreadLocal.set(deviceId);
    }

    public static String getSid() {
        return sidThreadLocal.get();
    }

    public static void setSid(String sid) {
        sidThreadLocal.set(sid);
    }

    public static Version getVersion() {
        return versionThreadLocal.get();
    }

    public static void setVersion(Version version) {
        versionThreadLocal.set(version);
    }

    public static ClientType getClientType() {
        return clientTypeThreadLocal.get();
    }

    public static void setClientType(ClientType clientType) {
        clientTypeThreadLocal.set(clientType);
    }

    public static Long getUserId() {
        return userIdThreadLocal.get();
    }

    public static void setUserId(Long userId) {
        userIdThreadLocal.set(userId);
    }
}
