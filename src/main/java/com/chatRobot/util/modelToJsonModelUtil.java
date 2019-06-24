package com.chatRobot.util;

import com.chatRobot.model.Order;
import com.chatRobot.model.jsonmodel.JsonOrder;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

public class modelToJsonModelUtil {
    public static JsonOrder orderToJsonOrder(Order order){
        JsonOrder jsonOrder = new JsonOrder();
        jsonOrder.setOrderTime(order.getOrderTime().toString());
        jsonOrder.setProductName(order.getProductName());
        jsonOrder.setProductId(order.getProductId());
        jsonOrder.setOrderId(order.getOrderId());
        jsonOrder.setEstimated(ToBitTwo(order.getEstimated()));
        jsonOrder.setChannel(order.getChannel());
        jsonOrder.setState(order.getState());
        jsonOrder.setFinishTime(order.getFinishTime().toString());
        jsonOrder.setWeixin(order.getWeixin());
        jsonOrder.setRefunds(order.getRefunds());
        jsonOrder.setIsSubmit(order.getIsSubmit());
        if(order.getSubmitTime()!= null) {
            jsonOrder.setSubmitTime(order.getSubmitTime().toString());
        }
        jsonOrder.setEntertime(order.getEntertime());
        jsonOrder.setUseraccount(order.getUseraccount());
        jsonOrder.setPicture(order.getPicture());

        return jsonOrder;
    }

    public static String ToBitTwo(String s){
        double temp = Double.parseDouble(s.substring(0,s.length()-1));
        BigDecimal bd = new BigDecimal(temp);
        Double tem = bd.setScale(2,BigDecimal.ROUND_FLOOR).doubleValue();

        return tem.toString()+"元";
    }
}
