package com.dabo.xunuo.util;

import org.junit.Test;

/**
 * 工具方法测试类
 * Created by zhangbin on 16/8/2.
 */
public class EncodeUtilsTest {


    @Test
    public void des() throws Exception {
        String str="1233333#hhhhhhh#111111";
        String key="key";

        String str2=EncodeUtils.aesEncrypt(str,key);

        System.out.println(str2);

        String str3=EncodeUtils.aesDecrypt(str2,key);
        System.out.println(str3);

    }
}
