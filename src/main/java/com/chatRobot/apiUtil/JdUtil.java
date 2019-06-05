package com.chatRobot.apiUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chatRobot.model.Order;
import com.chatRobot.service.JdauthoService;
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
public class JdUtil {//正式部署中去调用，部署在定时任务模块

    //注入Service
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private JdauthoService jdauthoService;

    private static String Jd_redirect_uri = "http://localhost:8080/ChatRobot/user/josauth";
    private static String Jd_SERVER_URL="https://api.jd.com/routerjson";
//    private static String Jd_appKey = "C2CD6961D2C32326CD837705D6BB7273";
//    private static String Jd_appSecret = "3e6a076050a24f1a89ee7ddbd314f561";
//    private static String jd_Access_token = "410610b8-4205-40e5-810b-98ea315a87b2";//token有效期为一年,暂时写死
//    private static int jdunionid = 1000189695;//后续从数据库中去获取，暂时写死

    public static List<Order> Monitoring_order(String start_time, String Jd_appKey, String Jd_appSecret, String jd_Access_token, int jdunionid, String useraccount) throws ParseException {//监控订单,返回接口中的数据，拼装成订单

        List<Order> orders = new ArrayList<Order>();
        String hasMore = "true";
        //jdunionid = Integer.parseInt(unionid);
        //jd_Access_token = Access_token;
        String orderInfo = "";
        System.out.println("unionid为"+jdunionid);
        int size=0;
        int pageIndex = 1;
        while (hasMore.equals("true")) {
            orderInfo = Util.jd_order(Jd_SERVER_URL, jd_Access_token, Jd_appKey, Jd_appSecret, jdunionid,pageIndex,start_time);
            System.out.println("未经处理的京东订单信息" + orderInfo);
            //装填json数据
            JSONObject jsonObject = JSON.parseObject(orderInfo);
            hasMore = jsonObject.getString("hasMore");
            JSONArray data = jsonObject.getJSONArray("data");
            if(data!=null){
                size = data.size();
            }else {
                size = 0;
            }

            for (int i = 0; i < size; i++) {
                JSONObject json = (JSONObject) data.get(i);
                JSONArray skuList = json.getJSONArray("skuList");
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                for (int j = 0; j < skuList.size(); j++) {//这里的业务逻辑，一个订单有可能拥有很多商品
                    JSONObject temp = (JSONObject) skuList.get(j);
                    Order order = new Order();
                    order.setChannel("京东");
                    java.util.Date dateorder = format.parse(TimeUtil.timeStamp2Date(json.getString("orderTime")));
                    java.sql.Timestamp ordertime = new java.sql.Timestamp(dateorder.getTime());
                    order.setOrderTime(ordertime);

                    //java.sql.Date finishtime = (java.sql.Date) format.parse(TimeUtil.timeStamp2Date(json.getString("finishTime")));
                    java.util.Date datefinish = format.parse(TimeUtil.timeStamp2Date(json.getString("finishTime")));
                    java.sql.Timestamp finishtime = new java.sql.Timestamp(datefinish.getTime());
                    order.setFinishTime(finishtime);
                    //String tempString = "";
                    //tempString = tempString+temp.getString("skuName");
                    order.setEstimated(temp.getString("payPrice")+"元");
                    order.setProductName(temp.getString("skuName"));
                    order.setProductId(temp.getString("skuId"));
                    order.setOrderId(json.getString("orderId"));
                    //order.setEstimated(json.getString("estimateFee"));
                    if (json.getString("validCode").equals("-1")) {
                        order.setState("未知");
                    } else if (json.getString("validCode").equals("2")) {
                        order.setState("无效-拆单");
                    } else if (json.getString("validCode").equals("3")) {
                        order.setState("无效-取消");
                    } else if (json.getString("validCode").equals("4")) {
                        order.setState("无效-京东帮帮主订单");
                    } else if (json.getString("validCode").equals("5")) {
                        order.setState("无效-账号异常");
                    } else if (json.getString("validCode").equals("6")) {
                        order.setState("无效-赠品类目不返佣");
                    } else if (json.getString("validCode").equals("7")) {
                        order.setState("无效-校园订单");
                    } else if (json.getString("validCode").equals("8")) {
                        order.setState("无效-企业订单");
                    } else if (json.getString("validCode").equals("9")) {
                        order.setState("无效-团购订单");
                    } else if (json.getString("validCode").equals("10")) {
                        order.setState("无效-开增值税专用发票订单");
                    } else if (json.getString("validCode").equals("11")) {
                        order.setState("无效-乡村推广员下单");
                    } else if (json.getString("validCode").equals("12")) {
                        order.setState("无效-自己推广自己下单");
                    } else if (json.getString("validCode").equals("13")) {
                        order.setState("无效-违规订单");
                    } else if (json.getString("validCode").equals("14")) {
                        order.setState("无效-来源与备案网址不符");
                    } else if (json.getString("validCode").equals("15")) {
                        order.setState("待付款");
                    } else if (json.getString("validCode").equals("16")) {
                        order.setState("已付款");
                    } else if (json.getString("validCode").equals("17")) {
                        order.setState("已完成");
                    } else if (json.getString("validCode").equals("18")) {
                        order.setState("已结算");
                    }

                    order.setUseraccount(useraccount);

                    //orderService.addOrder(order);
                    orders.add(order);
                }

            }
            pageIndex++;
        }
        return orders;
    }

}
