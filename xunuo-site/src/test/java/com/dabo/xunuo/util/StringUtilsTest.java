package com.dabo.xunuo.util;

import com.dabo.xunuo.common.Constants;
import org.junit.Test;

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
}
