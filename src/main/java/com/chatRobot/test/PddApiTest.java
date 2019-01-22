package com.chatRobot.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chatRobot.model.Order;
import com.chatRobot.util.TimeUtil;
import com.chatRobot.util.Util;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PddApiTest {

    private static String pdd_Access_token = "";
    private static String pdd_client_id = "1e3f5855199b47dd90e060343c690eef";
    private static String pdd_client_secret = "6293f7d6a22cac64d87ae1d95b5ed71e5bf7d7dd";

     public static List<Order> getApiData() throws ParseException {//测试用，不要在正式部署中去调用
         List<Order> orders = new ArrayList<Order>();
         int page_no = 1;//页面从第一页开始
         int count = 5;//随便取一个数字
         while (count>0){
             String PddOrderInfo = "";
             String start_time = "1546790400";//2019-01-07 00:00:00 起始有订单的时间
             String end_time = "1546876799";//2019-01-07 23:59:59
             PddOrderInfo = Util.get_pddOrderApi(start_time,end_time,pdd_client_id, pdd_Access_token, pdd_client_secret,page_no);
             System.out.println("拼多多订单信息：" + PddOrderInfo);
             JSONObject jsonObject = JSON.parseObject(PddOrderInfo);
             JSONObject response = jsonObject.getJSONObject("order_list_get_response");
             JSONArray order_list = response.getJSONArray("order_list");
             if(order_list!=null){
                 count = order_list.size();
             }else {
                 count = 0;
             }
             DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             for(int i=0;i<count;i++){
                 Order order = new Order();
                 JSONObject orderjson = (JSONObject)order_list.get(i);
                 order.setOrderId(orderjson.getString("order_sn"));

                 java.util.Date dateorder = format.parse(TimeUtil.ten_TimeStamp2Date(orderjson.getString("order_create_time")));
                 java.sql.Date ordertime = new java.sql.Date(dateorder.getTime());
                 order.setOrderTime(ordertime);

                 order.setProductName(orderjson.getString("goods_name"));
                 order.setProductId(orderjson.getString("goods_id"));
                 order.setEstimated(orderjson.getString("promotion_amount"));
                 order.setChannel("拼多多");
                 if(orderjson.getString("order_status").equals("-1")) {
                     order.setState("未支付");
                 }else if(orderjson.getString("order_status").equals("0")){
                     order.setState("已支付");
                 }else if(orderjson.getString("order_status").equals("1")){
                     order.setState("已成团");
                 }else if(orderjson.getString("order_status").equals("2")){
                     order.setState("确认收货");
                 }else if(orderjson.getString("order_status").equals("3")){
                     order.setState("审核成功");
                 }else if(orderjson.getString("order_status").equals("4")){
                     order.setState("审核失败（不可提现)");
                 }else if(orderjson.getString("order_status").equals("5")){
                     order.setState("已经结算");
                 }else if(orderjson.getString("order_status").equals("8")){
                     order.setState("非多多进宝商品（无佣金订单）");
                 }
                 orders.add(order);
             }
             page_no++;
         }
          return orders;
     }
}

