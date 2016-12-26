package com.dabo.xunuo.base.common.exception;

import com.dabo.xunuo.base.common.Constants;

/**
 * 系统异常封装
 * Created by zhangbin on 16/7/31.
 */
public class SysException extends Exception{

    private static final Integer DEFAULT_CODE = Constants.DEFAULT_CODE_ERROR;

    private final Integer errorCode;

    public SysException(String message) {
        super(message);
        this.errorCode = DEFAULT_CODE;
    }

    public SysException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = DEFAULT_CODE;
    }
    public SysException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    public SysException(String message, Integer errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    public Integer getErrorCode() {
        return errorCode;
    }
}
