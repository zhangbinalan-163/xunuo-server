package com.dabo.xunuo.util;

import com.alibaba.fastjson.JSONObject;
import com.dabo.xunuo.common.Constants;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;

/**
 * 工具方法测试类
 * Created by zhangbin on 16/8/2.
 */
public class StringUtilsTest {

    @Test
    public void md5(){
        String md5Str=StringUtils.md5(Constants.APP_KEY+"20110901");
        System.out.println(md5Str);
    }

    @Test
    public void testJson() throws UnsupportedEncodingException {
        String str="{\"rctModuleParams\":{\"userId\":6675783},\"rctModuleName\":\"homepage\"}";
        System.out.println(str);
        String str2=URLEncoder.encode(str, "utf-8");
        System.out.println(str2);
        String str3=URLDecoder.decode(str2, "utf-8");
        System.out.println(str3);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("key", Arrays.asList(1,1,1,1,0,0,0,0,0,0));
        str=JsonUtils.fromObject(jsonObject);
        System.out.println(str);
        str2=URLEncoder.encode(str, "utf-8");
        System.out.println(str2);
        str3=URLDecoder.decode(str2, "utf-8");
        System.out.println(str3);
    }

}
