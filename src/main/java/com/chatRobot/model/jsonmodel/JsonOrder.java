package com.chatRobot.model.jsonmodel;

import java.sql.Timestamp;
import java.util.Date;

public class JsonOrder {
    private String orderTime;//下单时间
    private String productName;//商品名称
    private String productId;//商品id
    private String orderId;//订单号
    private String Estimated;//预估金额
    private String channel;//订单渠道
    private String state;//订单状态
    private String finishTime;//完成时间
    private String weixin;//微信昵称
    private String Refunds;//返款状态
    private String isSubmit;//是否提交
    private String submitTime;//提交时间
    private String entertime;//入表时间
    private String useraccount;
    private String picture;

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEstimated() {
        return Estimated;
    }

    public void setEstimated(String estimated) {
        Estimated = estimated;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getRefunds() {
        return Refunds;
    }

    public void setRefunds(String refunds) {
        Refunds = refunds;
    }

    public String getIsSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(String isSubmit) {
        this.isSubmit = isSubmit;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getEntertime() {
        return entertime;
    }

    public void setEntertime(String entertime) {
        this.entertime = entertime;
    }

    public String getUseraccount() {
        return useraccount;
    }

    public void setUseraccount(String useraccount) {
        this.useraccount = useraccount;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
