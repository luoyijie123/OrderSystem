package com.chatRobot.apiUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chatRobot.model.Order;
import com.chatRobot.service.OrderService;
import com.chatRobot.service.UserService;
import com.chatRobot.util.Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class TaobaoUtil {//正式部署中去调用，部署在定时任务模块

    //注入Service
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    private static String taobao_session ="70000100134174d234d98ea80c4f8479edf67f36c1f32512ee2884e1f39844bb2ab2bdb2395126756";
                                           //70000101723758f95698eea4477ec9f1dbe1df81f8841969b74bedd883cb4e5eaddba662395126756
    public static List<Order> Monitoring_order(String date) throws ParseException {//监控订单,返回接口中的数据，拼装成订单
        List<Order> orderList = new ArrayList<Order>();
        //taobao_session = session;
        int page_no = 1;//页面从第一页开始
        int size = 5;//随便取一个数字
        String start_date = date;
        java.util.Date currentTime = new java.util.Date();
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        //String start_time = df.format(currentTime);//正式部署时传入
        System.out.println("session值为:"+taobao_session);
        do {
            String url = "http://api.tkurl.top/tbk_order?appkey=6oiyzUgz&start_time="+start_date+"&span=1200&session=" + taobao_session + "&page_no="+page_no+"&page_size=100";
            url = url.replaceAll(" ", "%20");
            String json = Util.loadJson(url);
            System.out.println("未经处理的淘宝订单信息:" + json);
            JSONObject jsonObject = JSON.parseObject(json);
            JSONObject response = jsonObject.getJSONObject("tbk_sc_order_get_response");
            JSONObject result = response.getJSONObject("results");
            JSONArray orders = result.getJSONArray("n_tbk_order");
            if(orders!=null) {
                size = orders.size();
            }else {
                size = 0;
            }
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(int i = 0;i<size;i++){
                Order order = new Order();
                JSONObject orderjson = (JSONObject) orders.get(i);
                java.util.Date dateorder = format.parse(orderjson.getString("create_time"));
                java.sql.Date ordertime = new java.sql.Date(dateorder.getTime());
                order.setOrderTime(ordertime);
                order.setProductName(orderjson.getString("item_title"));
                order.setProductId(orderjson.getString("num_iid"));
                order.setOrderId(orderjson.getString("trade_id"));
                order.setEstimated(orderjson.getString("commission")+"元");
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

                if(orderjson.containsKey("earning_time") && StringUtils.isNotBlank(orderjson.getString("earning_time"))) {
                    java.util.Date datefinish = format.parse(orderjson.getString("earning_time"));
                    java.sql.Date finishtime = new java.sql.Date(datefinish.getTime());
                    order.setFinishTime(finishtime);
                }
                orderList.add(order);
                //orderService.addOrder(order);
            }
            page_no++;
        }while (size>0);
        return orderList;
    }

}
