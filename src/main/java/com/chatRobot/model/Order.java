package com.chatRobot.model;

import java.sql.Date;

public class Order {
    private Date orderTime;//下单时间
    private String productName;//商品名称
    private String productId;//商品id
    private String orderId;//订单号
    private String Estimated;//预估金额
    private String channel;//订单渠道
    private String state;//订单状态
    private Date finishTime;//完成时间
    private String weixin;//微信昵称
    private String Refunds;//返款状态
    private String isSubmit;//是否提交
    private Date submitTime;//提交时间

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
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

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }
}