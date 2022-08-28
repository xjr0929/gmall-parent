package com.atguigu.gmall.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * @Author xjrstart
 * @Date 2022-08-28-16:29
 */
public class Jsons {

        private static ObjectMapper mapper = new ObjectMapper();
    //把对象转为json字符创
    public static String toStr(Object object) {
        // jackson

        try {
            String s = mapper.writeValueAsString(object);
            return s;
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}






















