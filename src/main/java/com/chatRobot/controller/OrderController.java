package com.chatRobot.controller;

import com.chatRobot.model.Order;
import com.chatRobot.service.OrderService;
import com.chatRobot.util.ExportExcel;
import com.chatRobot.util.JsonUtil;
import com.chatRobot.util.TimeUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/order")
public class OrderController {
    //注入Service
    @Autowired
    private OrderService orderService;

   @RequestMapping("uploadjddb")
    public String update(){//上传数据
       return "uploadjddb";
   }

   @RequestMapping("selectorder")
    public String SelectOrder(){//订单查询
       return "adminchaxun";
   }

    @RequestMapping("selectproduct")
    public String SelectProduct(){//按商品查询
        return "adminchaxun_byid";
    }

    @RequestMapping("checkuser")
    public String check_byuser(){
        return "redirect:/user/checkuser";
    }

    @RequestMapping("jiankong")
    public String jiankong_Manager(){
        return "redirect:/user/jiankong";
    }

    @RequestMapping("pdd_manager")
    public String pdd_manager(){
        return "redirect:/user/pdd_manager";
    }

    @RequestMapping("jd_manager")
    public String jd_manager(){
        return "redirect:/user/jd_manager";
    }

    @RequestMapping("tb_manager")
    public String tb_manager(){
        return "redirect:/user/tb_manager";
    }

    @RequestMapping("UserManager")
    public String UserManager(){
        return "redirect:/user/UserManager";
    }

    @RequestMapping("output")
    public String output(){

       return "tongjiexcel";
    }

    @RequestMapping("downloadExcel")
    @ResponseBody
    public File download(String txtBeginDate, String txtEndDate, String productid, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ExportExcel<Order> ex = new ExportExcel<Order>();
        String[] headers = {"下单时间","商品名称","商品id","订单号","预估金额","订单渠道","订单状态","完成时间","微信","返款状态","是否提交","提交时间"};
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String title = df.format(date);
        String filepath = "E:\\download\\"+title+".xls";
        List<Order> orders = new ArrayList<Order>();

        String beginTime = txtBeginDate;
        String endTime = txtEndDate;
        String productId = productid;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        java.util.Date beginTemp = format.parse(beginTime);
        java.sql.Date begin = new java.sql.Date(beginTemp.getTime());
        java.util.Date endTemp = format.parse(endTime);
        java.sql.Date end = new java.sql.Date(endTemp.getTime());

        if(productId.equals("")==false) {
            Map<String, Object> map = new HashMap<>();
            map.put("beginTime", begin);
            map.put("endTime", end);
            map.put("productId", productId);
            orders = orderService.FindOrderByTime(map);
        }else {
            Map<String, Object> map = new HashMap<>();
            map.put("beginTime", begin);
            map.put("endTime", end);
            orders = orderService.FindOrderByTimeNoproductid(map);
        }

        File file = new File(filepath);

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {

            e.printStackTrace();

        }

        try {
            OutputStream out = new FileOutputStream(file);
            ex.exportExcel("订单详情",headers,orders,out,"yyyy-MM-dd");
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    @RequestMapping("index")
    public String home(){
        return "redirect:/user/index";
    }

    @RequestMapping("ajaxsellerdingdan")
    public String getOrderByid(HttpServletResponse response, String dingdanlist) throws IOException {//从后台获取订单数据
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        String status ="";
        String data ="";
        String[] orderNos = dingdanlist.split("\\r?\\n");
        List<Order>totalOrders = new ArrayList<Order>();

        for(String orderNo:orderNos){
            List<Order>temp = new ArrayList<Order>();
            temp = orderService.FindOrderByOrderId(orderNo);

            for(int i=0;i<temp.size();i++){
                totalOrders.add(temp.get(i));
            }
        }

        if(totalOrders.size()==0){//大括号最后加
            status = "status: \"empty\"";
        }else {
            status = "status: \"ok\"";
        }
        for(Order order: totalOrders){
            String temp=JsonUtil.toJSON(order);
            data = data + temp+",";
        }

        if(data.equals("")==false) {
            data = data.substring(0, data.length() - 1);
        }
        data = "data:"+"["+data+"]";
        String json = "{"+status+","+ data+"}";

        JSONObject jsonObject = new JSONObject(json);
        out.println(jsonObject);
        out.flush();
        out.close();

        return "adminchaxun";
    }

    @RequestMapping("selectbyproductid")
    public String getOrderByproductid(HttpServletResponse response, String productid) throws IOException {//从后台获取订单数据
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        String status ="";
        String data ="";
        String productNo = productid;
        List<Order>totalOrders = new ArrayList<Order>();

        totalOrders = orderService.FindOrderByProductId(productNo);


        if(totalOrders.size()==0){//大括号最后加
            status = "status: \"empty\"";
        }else {
            status = "status: \"ok\"";
        }
        for(Order order: totalOrders){
            String temp=JsonUtil.toJSON(order);
            data = data + temp+",";
        }

        if(data.equals("")==false) {
            data = data.substring(0, data.length() - 1);
        }
        data = "data:"+"["+data+"]";
        String json = "{"+status+","+ data+"}";

        JSONObject jsonObject = new JSONObject(json);
        out.println(jsonObject);
        out.flush();
        out.close();

        return "adminchaxun_byid";
    }


}
