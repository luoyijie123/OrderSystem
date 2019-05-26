package com.chatRobot.apiUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chatRobot.model.Order;
import com.chatRobot.service.OrderService;
import com.chatRobot.service.UserService;
import com.chatRobot.util.TimeUtil;
import com.chatRobot.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class Pddutil {//正式部署中去调用，部署在定时任务模块

    //注入Service
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;
//    private static String pdd_Access_token = "";
//    private static String pdd_client_id = "1e3f5855199b47dd90e060343c690eef";
//    private static String pdd_client_secret = "6293f7d6a22cac64d87ae1d95b5ed71e5bf7d7dd";

    public static List<Order> Monitoring_order(String start_date, String end_date, String pdd_client_id, String pdd_client_secret) throws ParseException {//监控订单,返回接口中的数据，拼装成订单
        List<Order> orders = new ArrayList<Order>();
        int page_no = 1;//页面从第一页开始
        int count = 5;//随便取一个数字
        while (count>0){
            String PddOrderInfo = "";
            PddOrderInfo = Util.get_pddOrderApi(start_date,end_date,pdd_client_id,pdd_client_secret,page_no);
            System.out.println("未经处理的拼多多订单信息：" + PddOrderInfo);
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
                java.sql.Timestamp ordertime = new java.sql.Timestamp(dateorder.getTime());
                order.setOrderTime(ordertime);

                java.util.Date datefinish = format.parse(TimeUtil.ten_TimeStamp2Date(orderjson.getString("order_modify_at")));
                java.sql.Timestamp finishtime = new java.sql.Timestamp(datefinish.getTime());
                order.setFinishTime(finishtime);

                order.setProductName(orderjson.getString("goods_name"));
                order.setProductId(orderjson.getString("goods_id"));
                order.setEstimated(0.01*Integer.parseInt(orderjson.getString("order_amount"))+"元");
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
