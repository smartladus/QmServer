package com.smarladu.qmserver.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: QmServer
 * @description: JSON相关的格式转换工具
 * @author: Eason Wu
 * @create: 2021/9/27
 */

public class JsonUtils {
    private static final Pattern SNAKE_PATTERN = Pattern.compile("_(\\w)");
    private static final Pattern CAMEL_PATTERN = Pattern.compile("[A-Z]");
    /**
     * 将JAVA对象转换为下划线格式key的JSON对象
     * @param obj 需转化的Java对象
     * @return 下划线格式的JSON对象
     */
    public static Object toSnakeJsonObj(Object obj) {
        SerializeConfig config = new SerializeConfig();
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        return JSON.toJSON(obj, config);
    }

    /**
     * 下划线转驼峰
     * @param str 带下划线的字符串
     * @return 驼峰字符串
     */
    public static String snakeToCamel(String str) {
        str = str.toLowerCase();
        Matcher matcher = SNAKE_PATTERN.matcher(str);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     * @param str 驼峰字符串
     * @return 下划线字符串
     */
    public static String camelToSnake(String str) {
        Matcher matcher = CAMEL_PATTERN.matcher(str);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
