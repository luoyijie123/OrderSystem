package com.chatRobot.task;

import com.chatRobot.apiUtil.Pddutil;
import com.chatRobot.model.Jdautho;
import com.chatRobot.model.Order;
import com.chatRobot.model.Pddautho;
import com.chatRobot.service.OrderService;
import com.chatRobot.service.PddauthoService;
import com.chatRobot.service.UserService;
import com.chatRobot.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class PddTask {

    //注入Service
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PddauthoService pddauthoService;

    @Scheduled(cron = "0 0 21 * * ? ")//每天晚上21点检查遗漏订单,最近两天的订单
    public void checkOrder() throws ParseException {

        //获取所有账户的拼多多授权信息
        List<Pddautho> pddauthoList = pddauthoService.findAll();

        //check前一天的订单
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (Pddautho pddautho : pddauthoList) {
            Date date = new Date();//当前时间
            Date DayDate = dayStartDate(date);//当前时间的0点0分
            Date before_oneday_start = before_Oneday(DayDate);//前一天的0点0分
            Date before_oneday_end = getnowEndTime(before_oneday_start);
            //先转换为标准字符串格式
            String string_before_oneStart = sdf.format(before_oneday_start);
            String string_before_oneEnd = sdf.format(before_oneday_end);
            //再转换为时间戳格式
            String temp_before_oneStart = TimeUtil.StringToTimestamp(string_before_oneStart);//开始时刻
            String temp_before_oneEnd = TimeUtil.StringToTimestamp(string_before_oneEnd);//结束时刻
            List<Order> before_one_Apiorders = new ArrayList<Order>();
            before_one_Apiorders = Pddutil.Monitoring_order(temp_before_oneStart,temp_before_oneEnd,pddautho.getPddClientId(),pddautho.getPddClientSecret(), pddautho.getUserAccount());
            OrderFilter(before_one_Apiorders);
        }


        //check前两天的订单
        for (Pddautho pddautho : pddauthoList) {
            Date before_onedate = before_Oneday(new Date());//前一天时间
            Date before_oneDayDate = dayStartDate(before_onedate);//前一天时间的0点0分
            Date before_twoDaystart = before_Oneday(before_oneDayDate);//前两天的0点0分
            Date before_twoDayend = getnowEndTime(before_twoDaystart);//前两天的最后时刻
            //先转换为标准字符串格式
            String string_before_twoStart = sdf.format(before_twoDaystart);
            String string_before_twoEnd = sdf.format(before_twoDayend);
            //再转换为时间戳格式
            String temp_before_twoStart = TimeUtil.StringToTimestamp(string_before_twoStart);//开始时刻
            String temp_before_twoEnd = TimeUtil.StringToTimestamp(string_before_twoEnd);//结束时刻
            List<Order> before_two_Apiorders = new ArrayList<Order>();
            before_two_Apiorders = Pddutil.Monitoring_order(temp_before_twoStart, temp_before_twoEnd, pddautho.getPddClientId(), pddautho.getPddClientSecret(), pddautho.getUserAccount());
            OrderFilter(before_two_Apiorders);
        }
    }


    @Scheduled(cron= "0 0/5 * * * ? ")//间隔五分钟执行
    public void dailyOrder() throws ParseException {//全天候24小时监控订单，每隔五分钟获取一次近两小时的订单
        //获取所有账户的拼多多授权信息
        List <Pddautho> pddauthoList = pddauthoService.findAll();


        for (Pddautho pddautho : pddauthoList) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间处理模板
//            List<Order> orders = new ArrayList<Order>();//订单总数

            //获取当前时间之前120分钟之前的时间
            String now = df.format(new Date());
            Calendar beforeTime_120 = Calendar.getInstance();
            beforeTime_120.add(Calendar.MINUTE,-120);
            Date temp_120 = beforeTime_120.getTime();
            String date_beforeTime_120 = df.format(temp_120);//正式传入api接口的参数

            String deal_now = TimeUtil.StringToTimestamp(now);
            String deal_before120 = TimeUtil.StringToTimestamp(date_beforeTime_120);
            List<Order> orderList_120 = new ArrayList<Order>();
            orderList_120 = Pddutil.Monitoring_order(deal_before120, deal_now, pddautho.getPddClientId(), pddautho.getPddClientSecret(), pddautho.getUserAccount());

            OrderFilter(orderList_120);
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
