package com.dabo.xunuo.base.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * MD5加密
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            byte[] messageDigest = md.digest(str.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String tmp = Integer.toHexString(0xFF & messageDigest[i]);
                if (tmp.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(tmp);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 是否是合法手机号
     * @param mobile
     * @return
     */
    public static boolean isLegalMobile(String mobile) {
        if (mobile == null) {
            return false;
        }
        Pattern p = Pattern.compile("^(13|14|15|18|17)\\d{9}$");
        Matcher m = p.matcher(mobile);
        boolean b = m.matches();
        return b;
    }

    /**
     * 生成验证码
     * @return
     */
    public static String genCode() {
        int code = (int)(Math.random() * 899999 + 100000);
        return String.valueOf(code);
    }
}
