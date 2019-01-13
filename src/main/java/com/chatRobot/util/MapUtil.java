package com.chatRobot.util;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class MapUtil {
    /**
     * 让 Map按key进行排序
     */
     public static Map<String,String> sortMapByKey(Map<String,String> map){
         if(map==null || map.isEmpty()){
             return null;
         }
         Map<String,String>sortMap = new TreeMap<String, String>(new MapKeyComparator());
         sortMap.putAll(map);
         return sortMap;
     }
}
//实现一个比较器类
class MapKeyComparator implements Comparator<String> {
    @Override	public int compare(String s1, String s2) {
        return s1.compareTo(s2);  //从小到大排序	}
    }
}
