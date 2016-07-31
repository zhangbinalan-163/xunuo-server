package com.dabo.xunuo.common.exception;

/**
 * 系统异常封装
 * Created by zhangbin on 16/7/31.
 */
public class SysException extends Exception{
    public SysException() {
        super();
    }

    public SysException(String message) {
        super(message);
    }

    public SysException(String message, Throwable cause) {
        super(message, cause);
    }

    public SysException(Throwable cause) {
        super(cause);
    }
}
