package com.dabo.xunuo.web.vo;

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
}
