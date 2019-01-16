package com.chatRobot.Test;
//注入Service

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chatRobot.model.Order;
import com.chatRobot.service.OrderService;
import com.chatRobot.service.UserService;
import com.chatRobot.util.Util;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class TaobaoApiTest {

    @Autowired
    private static UserService userService;

    @Autowired
    private static OrderService orderService;

    //private String taobao_session;

    public static void getApiData(String taobao_session) throws ParseException {
        int page_no = 1;//页面从第一页开始
        int size = 5;//随便取一个数字
        java.util.Date currentTime = new java.util.Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String start_time = df.format(currentTime);//正式部署时传入
        System.out.println("session值为:"+taobao_session);
        do {
            String url = "http://api.tkurl.top/tbk_order?appkey=6oiyzUgz&start_time=2018-01-11 12:18:22&span=1200&session=" + taobao_session + "&page_no="+page_no+"&page_size=100";
            url = url.replaceAll(" ", "%20");
            String json = Util.loadJson(url);
            System.out.println("json值为:" + json);
            JSONObject jsonObject = JSON.parseObject(json);
            JSONObject response = jsonObject.getJSONObject("tbk_sc_order_get_response");
            JSONArray orders = response.getJSONArray("n_tbk_order");
            List<Order> orderList = new ArrayList<Order>();
            size = orders.size();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(int i = 0;i<orders.size();i++){
                Order order = new Order();
                JSONObject orderjson = (JSONObject) orders.get(i);
                java.util.Date dateorder = format.parse(orderjson.getString("create_time"));
                java.sql.Date ordertime = new java.sql.Date(dateorder.getTime());
                order.setOrderTime(ordertime);
                order.setProductName(orderjson.getString("item_title"));
                order.setProductId(orderjson.getString("num_iid"));
                order.setOrderId(orderjson.getString("trade_id"));
                order.setEstimated(orderjson.getString("commission"));
                order.setChannel("淘宝");
                if(orderjson.getString("tk_status").equals("3")) {
                    order.setState("订单结算");
                }else if (orderjson.getString("tk_status").equals("12")){
                    order.setState("订单付款");
                }else if (orderjson.getString("tk_status").equals("13")){
                    order.setState("订单失效");
                }else if (orderjson.getString("tk_status").equals("14")){
                    order.setState("订单成功");
                }
                java.util.Date datefinish = format.parse(orderjson.getString("earning_time"));
                java.sql.Date finishtime = new java.sql.Date(datefinish.getTime());
                order.setFinishTime(finishtime);
                orderService.addOrder(order);
            }
            page_no++;
        }while (size>0);
    }
}
