package com.dabo.xunuo.base.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * 加密解密工具
 * Created by zhangbin on 16/8/6.
 */
public class EncodeUtils {

    /**
     * AES加密为base 64 code
     * @param content
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        //1.构造密钥生成器，指定为AES算法,不区分大小写
        KeyGenerator keygen=KeyGenerator.getInstance("AES");

        //2.根据ecnodeRules规则初始化密钥生成器.生成一个128位的随机源,根据传入的字节数组
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(encryptKey.getBytes());
        keygen.init(128, random);

        //3.产生原始对称密钥
        SecretKey original_key=keygen.generateKey();

        //4.获得原始对称密钥的字节数组
        byte [] raw=original_key.getEncoded();

        //5.根据字节数组生成AES密钥
        SecretKey key=new SecretKeySpec(raw, "AES");

        //6.根据指定算法AES自成密码器
        Cipher cipher=Cipher.getInstance("AES");

        //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
        cipher.init(Cipher.ENCRYPT_MODE, key);

        //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
        byte [] byte_encode=content.getBytes("utf-8");

        //9.根据密码器的初始化方式--加密：将数据加密
        byte [] byte_AES=cipher.doFinal(byte_encode);

        //10.将加密后的数据转换为字符串
        Base64 base64=new Base64();
        String AES_encode=new String(base64.encode(byte_AES));
        //11.将字符串返回
        return AES_encode;
    }


    /**
     * 将base 64 code AES解密
     * @param encryptStr
     * @param decryptKey
     * @return
     * @throws Exception
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        //1.构造密钥生成器，指定为AES算法,不区分大小写
        KeyGenerator keygen=KeyGenerator.getInstance("AES");

        //2.根据ecnodeRules规则初始化密钥生成器 生成一个128位的随机源,根据传入的字节数组
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(decryptKey.getBytes());
        keygen.init(128, random);

        //3.产生原始对称密钥
        SecretKey original_key=keygen.generateKey();

        //4.获得原始对称密钥的字节数组
        byte [] raw=original_key.getEncoded();

        //5.根据字节数组生成AES密钥
        SecretKey key=new SecretKeySpec(raw, "AES");

        //6.根据指定算法AES自成密码器
        Cipher cipher=Cipher.getInstance("AES");

        //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
        cipher.init(Cipher.DECRYPT_MODE, key);

        //8.将加密并编码后的内容解码成字节数组
        Base64 base64=new Base64();
        byte [] byte_content= base64.decode(encryptStr);
        byte [] byte_decode=cipher.doFinal(byte_content);

        String AES_decode=new String(byte_decode,"utf-8");

        return AES_decode;
    }
}
