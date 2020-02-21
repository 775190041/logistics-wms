package com.nf.commons.uilts;

import org.apache.commons.codec.Charsets;

/**
 * 加密相关工具类直接使用Spring util封装，减少jar依赖
 */
public class DigestUtils extends org.springframework.util.DigestUtils {
    /**
     * 计算MD5摘要并以32个十六进制字符串的形式返回该值。
     * @param data
     * @return
     */
    public static String md5Hex(final String data) {
        return DigestUtils.md5DigestAsHex(data.getBytes(Charsets.UTF_8));
    }

    /**
     * 返回给定字节的MD5摘要的十六进制字符串表示形式。
     * @param bytes
     * @return
     */
    public static String md5Hex(final byte[] bytes) {
        return DigestUtils.md5DigestAsHex(bytes);
    }
}
