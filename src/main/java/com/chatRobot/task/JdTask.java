package com.chatRobot.task;

import com.chatRobot.apiUtil.JdUtil;
import com.chatRobot.model.Jdautho;
import com.chatRobot.model.Order;
import com.chatRobot.service.JdauthoService;
import com.chatRobot.service.OrderService;
import com.chatRobot.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class JdTask {

    //注入Service
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private JdauthoService jdauthoService;

    @Scheduled(cron = "0 0 1 * * ?")//每天凌晨一点钟准时执行,更新所有订单策略
    public void checkOrderAllTable() throws ParseException {
       //获取所有京东订单
        DateFormat format = new SimpleDateFormat("yyyyMMddhh");
        List<Order> orderList = orderService.FindOrderByOrderType("京东");
        for(Order order:orderList){
            if (StringUtils.isNotBlank(order.getEntertime())) {//最近新增的订单有入表时间
                String startTime = format.format(order.getEntertime());
                if(StringUtils.isNotBlank(order.getUseraccount())) {
                    List<Jdautho> jdauthoList = jdauthoService.selectByUserAccount(order.getUseraccount());
                    List<Order> Orders = null;
                    try {
                        for(Jdautho jdautho : jdauthoList) {
                            Orders = JdUtil.Monitoring_order(startTime, jdautho.getJdappkey(), jdautho.getJdappsecret(), jdautho.getJdaccesstoken(), Integer.parseInt(jdautho.getJdunionid()), jdautho.getUseraccount());
                        }
                    } catch (Exception e) {
                        continue;
                    }
                    OrderFilter(Orders);
                }
            }
        }

    }

    @Scheduled(cron = "0 0 21 * * ? ")//每天晚上21点检查遗漏订单,最近两天的订单
    public void checkOrder() throws ParseException {

        //获取所有账户的京东授权信息
        List <Jdautho> jdauthoList = jdauthoService.findAll();

        //check前一天的订单
        SimpleDateFormat sdf = null;
        for (Jdautho jdautho : jdauthoList){
            sdf = new SimpleDateFormat("yyyyMMddHH");
            Date date = new Date();//当前时间
            Date DayDate = dayStartDate(date);//当前时间的0点0分
            Date before_oneday_start = before_Oneday(DayDate);//前一天的0点0分
            while (before_oneday_start.compareTo(DayDate)<0){
                before_oneday_start = addHour(before_oneday_start,1);
                String string_before_oneday = sdf.format(before_oneday_start);//时间转换为需要的格式
                List<Order> oneday_orders = null;
                try {
                    oneday_orders = JdUtil.Monitoring_order(string_before_oneday, jdautho.getJdappkey(), jdautho.getJdappsecret(), jdautho.getJdaccesstoken(), Integer.parseInt(jdautho.getJdunionid()), jdautho.getUseraccount());
                } catch (Exception e) {
                    continue;
                }
                OrderFilter(oneday_orders);
            }
        }

        //check前两天的订单
        for (Jdautho jdautho : jdauthoList) {
            Date before_onedate = before_Oneday(new Date());//前一天时间
            Date before_oneDayDate = dayStartDate(before_onedate);//前一天时间的0点0分
            Date before_twoDaystart = before_Oneday(before_oneDayDate);//前两天的0点0分
            while(before_twoDaystart.compareTo(before_oneDayDate)<0) {
                before_twoDaystart = addHour(before_twoDaystart,1);
                String string_beforetwo = sdf.format(before_twoDaystart);//时间转换为需要的格式
                List<Order> twoday_orders = null;
                try {
                    twoday_orders = JdUtil.Monitoring_order(string_beforetwo, jdautho.getJdappkey(), jdautho.getJdappsecret(), jdautho.getJdaccesstoken(), Integer.parseInt(jdautho.getJdunionid()), jdautho.getUseraccount());
                } catch (Exception e) {
                    continue;
                }
                OrderFilter(twoday_orders);
            }
        }
    }

    @Scheduled(cron= "0 0/5 * * * ? ")//间隔五分钟执行
    public void dailyOrder() throws ParseException {//全天候24小时监控订单，每隔五分钟获取一次近两小时的订单
        //获取所有账户的京东授权信息
        List <Jdautho> jdauthoList = jdauthoService.findAll();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHH");//时间处理模板

        for (Jdautho jdautho : jdauthoList) {
            //京东这里的接口只要传入当前时间，实现实时监控即可
            List<Order> orders = new ArrayList<Order>();//订单总数
            String now_time = df.format(new Date());
            try {
                orders = JdUtil.Monitoring_order(now_time, jdautho.getJdappkey(), jdautho.getJdappsecret(), jdautho.getJdaccesstoken(), Integer.parseInt(jdautho.getJdunionid()), jdautho.getUseraccount());
            } catch (Exception e) {
                continue;
            }
            OrderFilter(orders);
        }
    }

    public void OrderFilter(List<Order> orders){//订单集群处理过滤器,传入的是api接口获取的订单

        List <Order> exist_orders = orderService.FindAll();
        List<Order>  ApiOrders = orders;

        List<Order> Insert_Orders = FindInsertOrder(ApiOrders,exist_orders);

        List<Order> Update_Orders = FindUpdateOrder(ApiOrders,exist_orders);

        for(int i=0;i<Insert_Orders.size();i++){
            orderService.addOrder(Insert_Orders.get(i));
        }

        for(int j=0;j<Update_Orders.size();j++){
            orderService.UpdateOrder(Update_Orders.get(j));
        }
    }

    public List<Order> FindInsertOrder(List<Order>ApiOrders,List<Order> exist_orders){//筛选出需要插入的订单

        List<Order> InsertOrders = new ArrayList<Order>();
        Iterator<Order> iterator = ApiOrders.iterator();

        while (iterator.hasNext()){
            Order order = iterator.next();
            for(int j=0;j<exist_orders.size();j++){
                if(order.getOrderId().equals(exist_orders.get(j).getOrderId())==true && order.getProductId().equals(exist_orders.get(j).getProductId())==true){//订单ID和商品ID若是相同直接删除元素
                    iterator.remove();
                }
            }
        }
        for(int i=0;i<ApiOrders.size();i++){
            InsertOrders.add(ApiOrders.get(i));
        }
        return InsertOrders;
    }

    public List<Order> FindUpdateOrder(List<Order>ApiOrders,List<Order> exist_orders){//筛选出需要更新的订单

        List<Order> UpdateOrders = new ArrayList<Order>();
        for(int i=0;i<ApiOrders.size();i++){
            for(int j=0;j<exist_orders.size();j++){
                if(ApiOrders.get(i).getOrderId().equals(exist_orders.get(j).getOrderId())==true && ApiOrders.get(i).getProductId().equals(exist_orders.get(j).getProductId())==true){//订单ID和商品ID若是相同直接插入
                    UpdateOrders.add(ApiOrders.get(i));
                }
            }
        }

        return UpdateOrders;
    }

    private static Date addTwetyMin(Date start, int offset) {
        Calendar c = Calendar.getInstance();
        c.setTime(start);
        c.add(Calendar.MINUTE, offset);
        return c.getTime();
    }

    private static Date addHour(Date start, int offset) {
        Calendar c = Calendar.getInstance();
        c.setTime(start);
        c.add(Calendar.HOUR, offset);
        return c.getTime();
    }

    private static Date before_Oneday(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }

    private static Date before_Twoday(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -2);
        return c.getTime();
    }

    private static Date dayStartDate(Date date) {//获取一天当中最开始的时间
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date getnowEndTime(Date date) {//获取一天当中的最后时刻
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.setTime(date);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }
}
