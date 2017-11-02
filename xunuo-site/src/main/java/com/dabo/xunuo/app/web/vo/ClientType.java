package com.dabo.xunuo.app.web.vo;

import com.dabo.xunuo.base.common.Constants;
import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.util.StringUtils;

/**
 * 客户端类型枚举类
 */
public enum ClientType {
    IPHONE(1, "IOS"), ANDROID(2, "ANDROID");

    private int id;
    private String name;

    ClientType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ClientType getInstance(String name) throws SysException {
        if (StringUtils.isEmpty(name)) {
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

    public int getId() {
        return id;
    }
}
