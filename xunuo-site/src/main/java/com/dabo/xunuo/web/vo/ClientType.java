package com.dabo.xunuo.web.vo;

import com.dabo.xunuo.common.Constants;
import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.util.StringUtils;

/**
 * 客户端类型枚举类
 */
public enum ClientType {
    IPHONE("IOS"), ANDROID("ANDROID");


    private String name;

    ClientType(String name) {
        this.name = name;
    }

    public static ClientType getInstance(String name) throws SysException {
        if(StringUtils.isEmpty(name)){
            throw new SysException("HEADER缺失", Constants.ERROR_CODE_INVALID_PARAM);
        }
        if (name.equals(IPHONE.getName())) {
            return IPHONE;
        }
        if (name.equals(ANDROID.getName())) {
            return ANDROID;
        }
        throw new SysException("不支持该客户端");
    }

    public String getName() {
        return name;
    }
}
