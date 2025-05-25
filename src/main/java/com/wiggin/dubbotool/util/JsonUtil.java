package com.wiggin.dubbotool.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @description: json工具类
 * @author: wiggin
 * @date: 2024-08-27 15:41
 **/
public class JsonUtil {

    public static String toJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {

        }
        return null;
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {

        }
        return null;
    }

}
