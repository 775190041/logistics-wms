package com.nf.commons.uilts;

import java.util.Collection;

/**
 * 继承自Spring util的工具类，减少jar依赖
 */
public class StringUtils extends org.springframework.util.StringUtils {

    public static boolean isBlank(final CharSequence cs) {
        return !StringUtils.isNotBlank(cs);
    }

    /**
     * 检查一个字符序列是不是空的("")，是不是空的，是不是只有空白。
     * @param cs
     * @return
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return StringUtils.hasText(cs);
    }

    /**
     * 将Collection 集合转换为带分隔符的String
     * @param coll
     * @param delim
     * @return
     */
    public static String join(Collection<?> coll, String delim) {
        return StringUtils.collectionToDelimitedString(coll, delim);
    }

    /**
     * 将 String数组转换为带分隔符的String
     * @param arr
     * @param delim
     * @return
     */
    public static String join(Object[] arr, String delim) {
        return StringUtils.arrayToDelimitedString(arr, delim);
    }
}
