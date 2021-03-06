package com.chatRobot.task;

import com.chatRobot.apiUtil.Pddutil;
import com.chatRobot.apiUtil.TaobaoUtil;
import com.chatRobot.model.Order;
import com.chatRobot.model.Pddautho;
import com.chatRobot.model.Tbautho;
import com.chatRobot.service.OrderService;
import com.chatRobot.service.TbauthoService;
import com.chatRobot.service.UserService;
import com.chatRobot.util.TimeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class TaobaoTask {

    //注入Service
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TbauthoService tbauthoService;

    @Scheduled(cron = "0 0 3 * * ? ")//每天凌晨三点钟准时执行，查找所有遗漏订单并且更新订单
    public void checkOrderAllTable() throws ParseException {
        //获取所有淘宝客订单
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Order> orderList = orderService.FindOrderByOrderType("淘宝");
        for(Order order:orderList){
            if (StringUtils.isNotBlank(order.getEntertime())) {//最近新增的订单有入表时间
                String startTime = order.getEntertime();
                if(StringUtils.isNotBlank(order.getUseraccount())) {
                    List<Tbautho> tbauthoList = tbauthoService.selectByUserAccount(order.getUseraccount());
                    List<Order> Orders = null;
                    try {
                        for(Tbautho tbautho : tbauthoList) {
                            Orders = TaobaoUtil.Monitoring_order(startTime, tbautho.getTaobaosession(), tbautho.getUseraccount());
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

        //获取所有账户的淘宝授权信息
        List<Tbautho> tbauthoList = tbauthoService.findAll();

        //check前一天的订单
        SimpleDateFormat sdf = null;
        for (Tbautho tbautho : tbauthoList) {
            Date date = new Date();//当前时间
            Date DayDate = dayStartDate(date);//当前时间的0点0分
            Date start = before_Oneday(DayDate);//前一天的0点0分
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while(start.compareTo(DayDate)<0){
                start = addTwetyMin(start,20);
                String deal_date = sdf.format(start);
                List<Order> Apidatas = null;
                try {
                    Apidatas = TaobaoUtil.Monitoring_order(deal_date, tbautho.getTaobaosession(), tbautho.getUseraccount());
                } catch (Exception e) {
                    continue;
                }
                OrderFilter(Apidatas);
            }
        }

        //check前两天的订单
        for (Tbautho tbautho : tbauthoList) {
            Date before_onedate = before_Oneday(new Date());//前一天时间
            Date before_oneDayDate = dayStartDate(before_onedate);//前一天时间的0点0分
            Date before_twoDaystart = before_Oneday(before_oneDayDate);//前两天的0点0分
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while(before_twoDaystart.compareTo(before_oneDayDate)<0){
                before_twoDaystart = addTwetyMin(before_twoDaystart,20);
                String deal_date2 = sdf.format(before_twoDaystart);
                List<Order> Apidatas_twodays = null;
                try {
                    Apidatas_twodays = TaobaoUtil.Monitoring_order(deal_date2, tbautho.getTaobaosession(), tbautho.getUseraccount());
                } catch (Exception e) {
                    continue;
                }
                OrderFilter(Apidatas_twodays);
            }
        }

        //check前三天的订单
        for (Tbautho tbautho : tbauthoList) {
            Date before_twodate = before_Twoday(new Date());//前二天时间
            Date before_twoDayDate = dayStartDate(before_twodate);//前二天时间的0点0分
            Date before_threeDaystart = before_Oneday(before_twoDayDate);//前三天的0点0分
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while(before_threeDaystart.compareTo(before_twoDayDate)<0){
                before_threeDaystart = addTwetyMin(before_threeDaystart,20);
                String deal_date2 = sdf.format(before_threeDaystart);
                List<Order> Apidatas_threedays = null;
                try {
                    Apidatas_threedays = TaobaoUtil.Monitoring_order(deal_date2, tbautho.getTaobaosession(), tbautho.getUseraccount());
                } catch (Exception e) {
                    continue;
                }
                OrderFilter(Apidatas_threedays);
            }
        }

    }

    @Scheduled(cron= "0 0/5 * * * ? ")//间隔五分钟执行
    public void dailyOrder() throws ParseException {//全天候24小时监控订单，每隔五分钟获取一次近两小时的订单
        //获取所有账户的淘宝授权信息
        List<Tbautho> tbauthoList = tbauthoService.findAll();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间处理模板
        List<Order> orders = new ArrayList<Order>();//订单总数

        //获取当前时间之前120分钟之前的时间
        for (Tbautho tbautho : tbauthoList) {
            try {
                Calendar beforeTime_120 = Calendar.getInstance();
                beforeTime_120.add(Calendar.MINUTE, -120);
                Date temp_120 = beforeTime_120.getTime();
                String date_beforeTime_120 = df.format(temp_120);//正式传入api接口的参数
                List<Order> orderList_120 = TaobaoUtil.Monitoring_order(date_beforeTime_120, tbautho.getTaobaosession(), tbautho.getUseraccount());
                for (int i = 0; i < orderList_120.size(); i++) {
                    orders.add(orderList_120.get(i));
                }
            }catch (Exception e){
                continue;
            }
        }

        //获取当前时间之前100分钟之前的时间
        Calendar beforeTime_100 = null;
        for (Tbautho tbautho : tbauthoList) {
            beforeTime_100 = Calendar.getInstance();
            beforeTime_100.add(Calendar.MINUTE,-100);
            Date temp_100 = beforeTime_100.getTime();
            String date_beforeTime_100 = df.format(temp_100);//正式传入api接口的参数
            List<Order> orderList_100 = null;
            try {
                orderList_100 = TaobaoUtil.Monitoring_order(date_beforeTime_100, tbautho.getTaobaosession(), tbautho.getUseraccount());
            } catch (Exception e) {
                continue;
            }
            for(int i=0;i<orderList_100.size();i++){
                orders.add(orderList_100.get(i));
            }
        }

        //获取当前时间之前80分钟之前的时间
        Calendar beforeTime_80 = Calendar.getInstance();
        for (Tbautho tbautho : tbauthoList) {
            beforeTime_80.add(Calendar.MINUTE,-80);
            Date temp_80 = beforeTime_100.getTime();
            String date_beforeTime_80 = df.format(temp_80);//正式传入api接口的参数
            List<Order> orderList_80 = null;
            try {
                orderList_80 = TaobaoUtil.Monitoring_order(date_beforeTime_80, tbautho.getTaobaosession(), tbautho.getUseraccount());
            } catch (Exception e) {
                continue;
            }
            for(int i=0;i<orderList_80.size();i++){
                orders.add(orderList_80.get(i));
            }
        }

        //获取当前时间之前60分钟之前的时间
        Calendar beforeTime_60 = Calendar.getInstance();
        for (Tbautho tbautho : tbauthoList) {
            beforeTime_60.add(Calendar.MINUTE,-60);
            Date temp_60 = beforeTime_60.getTime();
            String date_beforeTime_60 = df.format(temp_60);//正式传入api接口的参数
            List<Order> orderList_60 = null;
            try {
                orderList_60 = TaobaoUtil.Monitoring_order(date_beforeTime_60, tbautho.getTaobaosession(), tbautho.getUseraccount());
            } catch (Exception e) {
                continue;
            }
            for(int i=0;i<orderList_60.size();i++){
                orders.add(orderList_60.get(i));
            }
        }

        //获取当前时间之前40分钟之前的时间
        Calendar beforeTime_40 = Calendar.getInstance();
        for (Tbautho tbautho : tbauthoList) {
            beforeTime_40.add(Calendar.MINUTE,-40);
            Date temp_40 = beforeTime_40.getTime();
            String date_beforeTime_40 = df.format(temp_40);//正式传入api接口的参数
            List<Order> orderList_40 = null;
            try {
                orderList_40 = TaobaoUtil.Monitoring_order(date_beforeTime_40, tbautho.getTaobaosession(), tbautho.getUseraccount());
            } catch (Exception e) {
                continue;
            }
            for(int i=0;i<orderList_40.size();i++){
                orders.add(orderList_40.get(i));
            }
        }

        //获取当前时间之前20分钟之前的时间
        Calendar beforeTime_20 = Calendar.getInstance();
        for (Tbautho tbautho : tbauthoList) {
            beforeTime_20.add(Calendar.MINUTE,-20);
            Date temp_20 = beforeTime_20.getTime();
            String date_beforeTime_20 = df.format(temp_20);//正式传入api接口的参数
            List<Order> orderList_20 = null;
            try {
                orderList_20 = TaobaoUtil.Monitoring_order(date_beforeTime_20, tbautho.getTaobaosession(), tbautho.getUseraccount());
            } catch (Exception e) {
                continue;
            }
            for(int i=0;i<orderList_20.size();i++){
                orders.add(orderList_20.get(i));
            }
            OrderFilter(orders);
        }
    }

    public void OrderFilter(List<Order>orders){//订单集群处理过滤器,传入的是api接口获取的订单

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

    private static Date dayStartDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

}
