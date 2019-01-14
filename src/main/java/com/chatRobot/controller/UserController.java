package com.chatRobot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chatRobot.model.Order;
import com.chatRobot.model.User;
import com.chatRobot.service.OrderService;
import com.chatRobot.service.UserService;
import com.chatRobot.util.TimeUtil;
import com.chatRobot.util.Util;
//import org.json.JSONArray;
//import org.json.JSONObject;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    //注入Service
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;
    private String redirect_uri = "http://localhost:8080/ChatRobot/user/josauth";
    private String SERVER_URL="https://api.jd.com/routerjson";
    private String session;
    private String appKey = "C2CD6961D2C32326CD837705D6BB7273";
    private String appSecret = "3e6a076050a24f1a89ee7ddbd314f561";
    private String jd_Access_token = "";
    private String pdd_Access_token = "";
    private String pdd_client_id = "1e3f5855199b47dd90e060343c690eef";
    private String pdd_client_secret = "6293f7d6a22cac64d87ae1d95b5ed71e5bf7d7dd";
    private int jdunionid;

    @RequestMapping("add")
    public String addUser(User user, Model model){

        System.out.println("用户创建："+user.getAccount()+user.getPassword());
        userService.addUser(user);

        model.addAttribute("msg","注册成功");
        //注册成功后跳转success.jsp页面

        return "success";
    }

    @RequestMapping("login")
    public String login(){
       return "login";
    }

    @RequestMapping("userpagesetting")
    public String userpagesetting(){
        return "userpagesetting";
    }


    @RequestMapping("josauth")
    public String backjos(HttpServletRequest request){//获取京东的access_taken
        String code ="";
        String state = "";
        String json = "";
        String result = "?后面的参数为:" + request.getQueryString();
        System.out.println(result);
        Map<String, String> mapRequest = Util.URLRequest(request.getQueryString());
        code = mapRequest.get("code");
        state = mapRequest.get("state");
        json = Util.jd_Json(appKey,appSecret,redirect_uri,code,state);
        System.out.println(code);
        System.out.println(json);
        this.jd_Access_token = Util.jd_Access_token(json);
        System.out.println(jd_Access_token);

        return "josauth";
    }

    @RequestMapping("pinduoduoauth")
    public String backpingduo(HttpServletRequest request){

        String code="";
        String json = "";
        String result = "?后面的参数为:" + request.getQueryString();
        System.out.println(result);
        Map<String, String> mapRequest = Util.URLRequest(request.getQueryString());
        code = mapRequest.get("code");
        System.out.println("code值为"+code);
        json = Util.pdd_Json(code);
        System.out.println("json值为"+json);
        this.pdd_Access_token = Util.pingduoduo_Access_token(json);
        System.out.println("拼多多Access_token值为"+this.pdd_Access_token);

        return "pinduoduoauth";
    }



    @RequestMapping("checklogin")
    public String checklogin(String account,String password,Model model){
        System.out.println("用户登录："+account+password);

        User user=userService.login(account,password);
        if(user!=null) {
            model.addAttribute("msg", "登录成功");
            return "index";
        }else {
            return "login";
        }
    }

    @RequestMapping("index")
    public String home(){
        System.out.println("session值为:"+this.session);
        return "index";
    }

    @RequestMapping("checkuser")
    public String check_byuser(){
        return "admincheck_byuser";
    }

    @RequestMapping("uploadjddb")
    public String update(){
        return "redirect:/order/uploadjddb";
    }

    @RequestMapping("selectorder")
    public String SelectOrder(){
        return "redirect:/order/selectorder";
    }

    @RequestMapping("selectproduct")
    public String SelectProduct(){
        return "redirect:/order/selectorder";
    }

    @RequestMapping("output")
    public String output(){
        return "redirect:/order/output";
    }

    @RequestMapping("taobaoLogin")
    public String taobaoLogin(){
        return "redirect:http://api.tkurl.top/authorize?type=1&appkey=6oiyzUgz";
    }

    @RequestMapping("josLogin")
    public String jingdongLogin(){
        return "redirect:https://oauth.jd.com/oauth/authorize?response_type=code&client_id=C2CD6961D2C32326CD837705D6BB7273&redirect_uri=http://localhost:8080/ChatRobot/user/josauth&state=quanyi";
    }

    @RequestMapping("pingduoduoLogin")
    public String pingLogin(){
        return "redirect:https://jinbao.pinduoduo.com/open.html?client_id=1e3f5855199b47dd90e060343c690eef&response_type=code&redirect_uri=http://localhost:8080/ChatRobot/user/pinduoduoauth";
    }


    @RequestMapping("/ajaxsettaobaosession")
    public String getTaobaoSession(String session){
        this.session = session;
        java.util.Date currentTime = new java.util.Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String start_time = df.format(currentTime);//正式部署时传入url

        System.out.println("session值为:"+session);
        String url = "http://api.tkurl.top/tbk_order?appkey=6oiyzUgz&start_time=2018-01-11 12:18:22&span=1200&session="+session+"&page_no=";
        url = url.replaceAll(" ", "%20");
        String json = Util.loadJson(url);
        System.out.println("json值为:"+json);
        return "index";
    }

    @RequestMapping("ajaxsetuniodid")
    public String getJduniodid(String unionid) throws ParseException {
        String hasMore = "true";
        jdunionid = Integer.parseInt(unionid);
        String orderInfo = "";
        System.out.println("unionid为"+jdunionid);
        int pageIndex = 1;
        while (hasMore.equals("true")) {
            orderInfo = Util.jd_order(SERVER_URL, this.jd_Access_token, appKey, appSecret, jdunionid,pageIndex);
            System.out.println("未经处理的订单信息" + orderInfo);
            //装填json数据
            JSONObject jsonObject = JSON.parseObject(orderInfo);
            hasMore = jsonObject.getString("hasMore");
            JSONArray data = jsonObject.getJSONArray("data");
            List<Order> orders = new ArrayList<Order>();
            for (int i = 0; i < data.size(); i++) {
                JSONObject json = (JSONObject) data.get(i);
                JSONArray skuList = json.getJSONArray("skuList");
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                for (int j = 0; j < skuList.size(); j++) {//这里的业务逻辑，一个订单有可能拥有很多商品
                    JSONObject temp = (JSONObject) skuList.get(j);
                    Order order = new Order();
                    order.setChannel("京东");
                    java.util.Date dateorder = format.parse(TimeUtil.timeStamp2Date(json.getString("orderTime")));
                    java.sql.Date ordertime = new java.sql.Date(dateorder.getTime());
                    order.setOrderTime(ordertime);

                    //java.sql.Date finishtime = (java.sql.Date) format.parse(TimeUtil.timeStamp2Date(json.getString("finishTime")));
                    java.util.Date datefinish = format.parse(TimeUtil.timeStamp2Date(json.getString("finishTime")));
                    java.sql.Date finishtime = new java.sql.Date(datefinish.getTime());
                    order.setFinishTime(finishtime);
                    //String tempString = "";
                    //tempString = tempString+temp.getString("skuName");
                    order.setEstimated(temp.getString("estimateFee"));
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

                    orderService.addOrder(order);
                    //orders.add(order);
                }

            }
            pageIndex++;
        }
        //for(int i=0;i<orders.size();i++){
           //System.out.println("订单号："+orders.get(i).getOrderId());
        //}

        return "index";
    }

    @RequestMapping("PddShouquan")
    public String PddShouquan(){

        String PddOrderInfo = "";

        PddOrderInfo = Util.get_pddOrderApi(pdd_client_id,pdd_Access_token,pdd_client_secret);

        System.out.println("拼多多订单信息："+PddOrderInfo);
        return "index";
    }

}
