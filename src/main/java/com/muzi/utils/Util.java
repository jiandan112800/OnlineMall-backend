package com.muzi.utils;

/**
 * 工具类
 */
public class Util {
    
    /**
     * 判断字符串是否为空
     * @param str 待判断的字符串
     * @return 如果字符串为null、空字符串或只包含空白字符，返回true；否则返回false
     */
    public static boolean isEmptyString(String str) {
        return str == null || str.trim().isEmpty();
    }
}




