package com.chatRobot.util;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.StringWriter;

public class JsonUtil {

    /**
     * 将对象转换为json格式字符串
     *
     * @param Object
     * @return json string
     */
    public static String toJSON(Object obj) {
        ObjectMapper om = new ObjectMapper();
        try {
            String json = om.writeValueAsString(obj);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * 将json形式字符串转换为java实体类
     *
     */
    public static <T> T parse(String jsonStr, Class<T> clazz) {
        ObjectMapper om = new ObjectMapper();
        T readValue = null;
        try {
            readValue = om.readValue(jsonStr, clazz);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readValue;
    }


}
