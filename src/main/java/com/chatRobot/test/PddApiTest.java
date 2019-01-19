package com.chatRobot.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chatRobot.model.Order;
import com.chatRobot.util.Util;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.ArrayList;
import java.util.List;

public class PddApiTest {

    private static String pdd_Access_token = "";
    private static String pdd_client_id = "1e3f5855199b47dd90e060343c690eef";
    private static String pdd_client_secret = "6293f7d6a22cac64d87ae1d95b5ed71e5bf7d7dd";

     public static void getApiData(){
         List<Order> orders = new ArrayList<Order>();
         int page_no = 1;//页面从第一页开始
         int count = 5;//随便取一个数字
         while (count>0){
             String PddOrderInfo = "";
             PddOrderInfo = Util.get_pddOrderApi(pdd_client_id, pdd_Access_token, pdd_client_secret,page_no);
             System.out.println("拼多多订单信息：" + PddOrderInfo);
             JSONObject jsonObject = JSON.parseObject(PddOrderInfo);
             JSONObject response = jsonObject.getJSONObject("order_list_get_response");
             JSONArray order_list = response.getJSONArray("order_list");
             if(order_list!=null){
                 count = order_list.size();
             }else {
                 count = 0;
             }
             for(int i=0;i<count;i++){
                 Order order = new Order();
                 JSONObject orderjson = (JSONObject)order_list.get(i);
             }
             page_no++;
         }

     }
}

