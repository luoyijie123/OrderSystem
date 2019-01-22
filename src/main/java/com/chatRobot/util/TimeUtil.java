package com.chatRobot.util;

//import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Locale;

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

    public static String timeStamp2Date(String time) {//将13位UNIX时间戳转换为正常的时间
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

    public static String ten_TimeStamp2Date(String timestampString) {//将10位UNIX时间戳转换为正常的时间
        String formats = "yyyy-MM-dd HH:mm:ss";
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }


    /**
     * String(yyyy-MM-dd HH:mm:ss)转10位时间戳
     * @param time
     * @return
     */
    public static String StringToTimestamp(String time){
        String times = "";
        try {
            times = Long.toString((Timestamp.valueOf(time).getTime())/1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(times==""){
            System.out.println("String转10位时间戳失败");
        }

        return times;

    }

}
