package com.chatRobot.util;

import org.springframework.util.DigestUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 接口请求sign
 * @author wangdan
 */
public class SignUtils {
    /**
     * 拼多多sign规则：clientScreet + key + value + ... + key + value + clientScreet
     * 规则说明：key按首字母升序排列,首尾加上client_secret,MD5加密后取大写
     * @param map
     * @param clientScreet
     * @return
     */
    public static String getPddSign(Map<String, Object> map, String clientScreet) {
        if(map==null){
            return null;
        }
        List<String> keyList = new ArrayList<>(map.keySet());
        Collections.sort(keyList);
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<keyList.size(); i++){
            String key = keyList.get(i);
            Object value = map.get(key);
            sb.append(key+value);
        }
        String signStr = clientScreet + sb + clientScreet;
        String md5Str = null;
        try {
            md5Str = DigestUtils.md5DigestAsHex(new ByteArrayInputStream(signStr.getBytes())).toUpperCase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return md5Str;

    }
}
