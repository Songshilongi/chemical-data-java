package com.songshilong.module.starter.common.utils;


import com.songshilong.module.starter.common.enums.SecurityExceptionEnum;
import com.songshilong.module.starter.common.exception.ClientException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.module.starter.common.utils
 * @Author: Ice, Song
 * @CreateTime: 2025-03-27  15:11
 * @Description: Md5SecurityUtil-加密和解密工具类
 * @Version: 1.0
 */
public class Md5SecurityUtil {


    /**
     * 获取普通的MD5加密值
     * @param source 需要被加密的字符串
     * @return 加密后的字符串
     */
    public static String getMd5Value(String source) {
        MessageDigest md5 = null;
        String result = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new ClientException(SecurityExceptionEnum.FAIL_GET_MD5_ORIGIN);
        }
        byte[] sourceByteArray = source.getBytes(StandardCharsets.UTF_8);
        byte[] digest = md5.digest(sourceByteArray);
        StringBuilder builder = new StringBuilder();
        for (byte b : digest) {
            String hex = String.format("%02x", b & 0xFF);
            builder.append(hex);
        }
        result = builder.toString();
        return result;
    }


    /**
     * 获取带盐的MD5加密值
     * @param source 需要被加密的字符串
     * @return 加密后的字符串
     */
    public static String getMd5ValueWithSalt(String source) {
        Random random = new Random();
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            salt.append(random.nextInt(Integer.MAX_VALUE));
        }
        return Md5SecurityUtil.getMd5Value(source + salt.toString());
    }
}
