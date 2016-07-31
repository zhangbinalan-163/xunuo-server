package com.dabo.xunuo.util;

import java.util.UUID;

/**
 * 字符创工具类
 */
public class StringUtils {
    /**
     * 字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if(null==str||"".equals(str)){
            return true;
        }
        return false;
    }

    /**
     * 产生一个UUID,重复概率如何?
     * @return
     */
    public static String uuid(){
        return UUID.randomUUID().toString();
    }
}
