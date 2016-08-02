package com.dabo.xunuo.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.Map;

/**
 * 请求签名工具类
 * Created by zhangbin on 16/8/2.
 */
public class SignUtils {
    /**
     * 对map按照key字段排序,生成SHA1
     *
     * @param paramMap
     * @param appKey
     * @param appSecret
     * @return
     */
    public static String generateSign(Map<String, String> paramMap,String appKey,String appSecret){
        //对paramMap排序
        String[] keyArray = paramMap.keySet().toArray(new String[paramMap.size()]);
        Arrays.sort(keyArray);
        // 拼接有序的参数名-值串
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(appKey).append("&");
        for (String key : keyArray) {
            stringBuilder.append(key).append("=").append(paramMap.get(key)).append("&");
        }
        stringBuilder.append(appSecret);
        String codes = stringBuilder.toString();
        //
        return DigestUtils.sha1Hex(codes).toUpperCase();
    }
}
