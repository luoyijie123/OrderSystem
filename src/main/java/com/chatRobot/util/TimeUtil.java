package com.chatRobot.util;

//import java.sql.Date;
import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class TimeUtil {

    public static void main(String[] args){
        System.out.println(UnixTimestamp());
    }

    public static String UnixTimestamp(){//获取当前时间的UnixTimestamp
        //初始化时区对象，北京时间是UTC+8，所以入参为8
        ZoneOffset zoneOffset=ZoneOffset.ofHours(8);
        //初始化LocalDateTime对象
        LocalDateTime localDateTime=LocalDateTime.now();
        //获取LocalDateTime对象对应时区的Unix时间戳
        return Long.toString(localDateTime.toEpochSecond(zoneOffset));
    }

    public static String timeStamp2Date(String time) {//将13位时间戳转换为正常的时间
        Long timeLong = Long.parseLong(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//要转换的时间格式
        Date date;
        try {
            date = sdf.parse(sdf.format(timeLong));
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
