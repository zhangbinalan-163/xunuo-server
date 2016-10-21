package com.dabo.xunuo.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 接口响应实体
 */
public class DataResponse {
    @JSONField(name = "err_msg")
    private String errMsg; // 消息
    @JSONField(name = "err_code")
    private int errorCode; // 错误码
    private Object data; // 返回对象
    @JSONField(name = "trace_id")
    private String traceId;//错误跟踪码

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
