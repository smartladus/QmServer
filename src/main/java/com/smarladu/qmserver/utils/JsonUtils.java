package com.smarladu.qmserver.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;

/**
 * @program: QmServer
 * @description:
 * @author: Eason Wu
 * @create: 2021/9/27
 */

public class JsonUtils {
    /**
     * 将JAVA对象转换为下划线格式key的JSON对象
     * @param obj 需转化的Java对象
     * @return
     */
    public static Object toSnakeJsonObj(Object obj) {
        SerializeConfig config = new SerializeConfig();
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        return JSON.toJSON(obj, config);
    }
}
