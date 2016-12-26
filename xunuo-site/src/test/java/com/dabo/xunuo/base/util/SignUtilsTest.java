package com.dabo.xunuo.base.util;


import com.dabo.xunuo.base.common.Constants;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 工具方法测试类
 * Created by zhangbin on 16/8/2.
 */
public class SignUtilsTest {


    @Test
    public void sign(){
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("format", "json");
        paramMap.put("city", "上海");
        paramMap.put("latitude", "31.21524");
        paramMap.put("longitude", "121.420033");
        paramMap.put("category", "美食");
        paramMap.put("region", "长宁区");
        paramMap.put("limit", "20");
        paramMap.put("radius", "2000");
        paramMap.put("offset_type", "0");
        paramMap.put("has_coupon", "1");
        paramMap.put("has_deal", "1");
        paramMap.put("keyword", "泰国菜");
        paramMap.put("sort", "7");

        String sign=SignUtils.generateSign(paramMap, Constants.APP_KEY,Constants.APP_SECRET);

        System.out.println(sign);
    }
}
