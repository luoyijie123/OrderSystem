package com.chatRobot.controller;

import com.chatRobot.model.*;
import com.chatRobot.service.*;
import com.chatRobot.test.TaobaoApiTest;
import com.chatRobot.util.Util;
//import org.json.JSONArray;
//import org.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
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

    @Autowired
    private JdauthoService jdauthoService;

    @Autowired
    private PddauthoService pddauthoService;

    @Autowired
    private TbauthoService tbauthoService;

    private String taobao_session;

    private String basePath;

    private String Jd_redirect_uri = basePath+"user/josauth";
    private String Jd_SERVER_URL="https://api.jd.com/routerjson";
//    private String Jd_appKey = "C2CD6961D2C32326CD837705D6BB7273";
//    private String Jd_appSecret = "3e6a076050a24f1a89ee7ddbd314f561";
//    private String jd_Access_token = "";
//    private int jdunionid;
//    private String Jd_appKey ="";
//    private String Jd_appSecret = "";
//    private String jd_Access_token = "";
//    private int jdunionid;

    private String pdd_Access_token = "";
    private String pdd_client_id = "1e3f5855199b47dd90e060343c690eef";
    private String pdd_client_secret = "6293f7d6a22cac64d87ae1d95b5ed71e5bf7d7dd";


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
    public ModelAndView userpagesetting(HttpServletRequest request,Model model){
        User olduser = (User)request.getSession().getAttribute("User_session");

        User newUser = userService.getUserByaccount(olduser.getAccount());
        model.addAttribute("user",newUser);

        return new ModelAndView("userpagesetting","userModel",model);
    }

    @RequestMapping("userupdate")
    public ModelAndView userupdate(HttpServletRequest request,Model model,String weixinAccount,String name,String weixinName,String zhifubao,String phone,String email,String team){
        User olduser = (User)request.getSession().getAttribute("User_session");

        User newUser = userService.getUserByaccount(olduser.getAccount());
        newUser.setWeixing_account(weixinAccount);
        newUser.setName(name);
        newUser.setWeixing_name(weixinName);
        newUser.setZhifubao_account(zhifubao);
        newUser.setPhone(phone);
        newUser.setEmail(email);
        newUser.setTeam_name(team);


        userService.updateUser(newUser);
        model.addAttribute("user",newUser);

        return new ModelAndView("userpagesetting","userModel",model);
    }


    @RequestMapping("josauth")
    public ModelAndView loback_Josauth(HttpServletRequest request,Model model,HttpSession session,String JdAppkey,String JdappSecret,String jdunionid){//获取京东的access_taken

        User currentUser = (User)request.getSession().getAttribute("User_session");
        Jdautho jdautho = jdauthoService.selectByUserAccount(currentUser.getAccount());
        String code ="";
        String state = "";
        String json = "";
        String result = "?后面的参数为:" + request.getQueryString();
        System.out.println(result);
        Map<String, String> mapRequest = Util.URLRequest(request.getQueryString());
        code = mapRequest.get("code");
        state = mapRequest.get("state");
//        json = Util.jd_Json(Jd_appKey,Jd_appSecret,Jd_redirect_uri,code,state);
        json = Util.jd_Json(jdautho.getJdAppkey(),jdautho.getJdAppsecret(),Jd_redirect_uri,code,state);
        System.out.println(code);
        System.out.println(json);
//        this.jd_Access_token = Util.jd_Access_token(json);
        String jd_accessToken = Util.jd_Access_token(json);
        jdautho.setJdAccessToken(jd_accessToken);
        jdauthoService.update(jdautho);
        System.out.println(jd_accessToken);

        return new ModelAndView("josauth");
    }

    @RequestMapping("pinduoduoauth")
    public String backpingduo(HttpServletRequest request){

        User currentUser = (User) request.getSession().getAttribute("User_session");
        Pddautho pddautho = pddauthoService.selectByAccount(currentUser.getAccount());

        String code="";
        String json = "";
        String result = "?后面的参数为:" + request.getQueryString();
        System.out.println(result);
        Map<String, String> mapRequest = Util.URLRequest(request.getQueryString());
        code = mapRequest.get("code");
        System.out.println("code值为"+code);
        json = Util.pdd_Json(pddautho.getPddClientId(),pddautho.getPddClientSecret(),code);
        System.out.println("json值为"+json);
        this.pdd_Access_token = Util.pingduoduo_Access_token(json);
        System.out.println("拼多多Access_token值为"+this.pdd_Access_token);

        return "pinduoduoauth";
    }



    @RequestMapping("checklogin")
    public ModelAndView checklogin(String account, String password, Model model,HttpServletRequest request){
        System.out.println("用户登录："+account+password);
        String path = request.getContextPath();
        this.basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        System.out.println("项目根路径:"+basePath);
        Util.setBasePath(this.basePath);
        User user=userService.login(account,password);
        if(user!=null) {
            model.addAttribute("msg", "登录成功");
            request.getSession().setAttribute("User_session", user);
            if(!StringUtils.isNotBlank(user.getUserlink())){
//                UUID uuid = UUID.randomUUID();
                user.setUserlink(basePath+"client/clientHistoryOrder/YBJs8QyLqq"+user.getAccount());
                userService.updateUser(user);
            }
            return new ModelAndView("index");
        }else {
            return new ModelAndView("login");
        }
    }

    @RequestMapping("setlink")
    public ModelAndView setlink(HttpServletRequest request,Model model){

        User olduser = (User)request.getSession().getAttribute("User_session");
        User newUser = userService.getUserByaccount(olduser.getAccount());
        model.addAttribute("user",newUser);
        return new ModelAndView("index","linkmodel",model);
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request){
       request.getSession().removeAttribute("User_session");
       return "login";
    }

    @RequestMapping("index")
    public String home(HttpServletRequest request){
        String path = request.getContextPath();
        System.out.println(basePath);
        System.out.println("淘宝session值为:"+this.taobao_session);
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

    @RequestMapping("jdshouquan")
    public String jdshouquan(){
        return "jdshouquan";
    }

    @RequestMapping("jdshouquan_operation")
    public ModelAndView jdshouquan_operation(HttpServletRequest request,String JdAppkey,String JdappSecret,String jdunionid,String name,String phone){

        User currentUser = (User)request.getSession().getAttribute("User_session");

        Jdautho jdautho = new Jdautho();
        jdautho.setUserAccount(currentUser.getAccount());
        jdautho.setJdAppkey(JdAppkey);
        jdautho.setJdAppsecret(JdappSecret);
        jdautho.setJdunionid(jdunionid);
        jdautho.setName(name);
        jdautho.setPhone(phone);

        jdauthoService.AddJdautho(jdautho);

        return new ModelAndView("josLogin");

    }

    @RequestMapping("tbshouquan")
    public String tbshouquan(){
        return "tbshouquan";
    }

    @RequestMapping("tbshouquan_operation")
    public ModelAndView tbshouquan_operation(HttpServletRequest request,String taobaoSession,String taobaoAccount,String name,String phone){

        User currentUser = (User)request.getSession().getAttribute("User_session");

        Tbautho tbautho = new Tbautho();
        tbautho.setUserAccount(currentUser.getAccount());
        tbautho.setTaobaoSession(taobaoSession);
        tbautho.setTaobaoAccount(taobaoAccount);
        tbautho.setName(name);
        tbautho.setPhone(phone);

        tbauthoService.AddTbautho(tbautho);

        return new ModelAndView("index");
    }

    @RequestMapping("pddshouquan")
    public String pddshouquan(){

        return "pddshouquan";
    }

    @RequestMapping("pddshouquan_operation")
    public ModelAndView pddshouquan_operation(HttpServletRequest request,String pdd_client_id,String pdd_client_secret,String name,String phone){

        User currentUser = (User)request.getSession().getAttribute("User_session");

        Pddautho pddautho = new Pddautho();
        pddautho.setUserAccount(currentUser.getAccount());
        pddautho.setPddClientId(pdd_client_id);
        pddautho.setPddClientSecret(pdd_client_secret);
        pddautho.setName(name);
        pddautho.setPhone(phone);

        pddauthoService.AddPddautho(pddautho);

        return new ModelAndView("index");
    }

    @RequestMapping("datachaxun")
    public String datachaxun(){
        return "redirect:/order/datachaxun";
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
    public String getTaobaoSession(HttpServletResponse response, String session) throws ParseException, IOException {//获取淘宝授权Session，暂不开启，后续写入数据库
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();

        this.taobao_session = session;
        TaobaoApiTest taobaoApiTest = new TaobaoApiTest();
        /*int page_no = 1;//页面从第一页开始
        int size = 5;//随便取一个数字
        java.util.Date currentTime = new java.util.Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String start_time = df.format(currentTime);//正式部署时传入
        System.out.println("session值为:"+taobao_session);
        do {
           String url = "http://api.tkurl.top/tbk_order?appkey=6oiyzUgz&start_time=2018-01-11 12:18:22&span=1200&session=" + this.taobao_session + "&page_no="+page_no+"&page_size=100";
           url = url.replaceAll(" ", "%20");
           String json = Util.loadJson(url);
           System.out.println("json值为:" + json);
           JSONObject jsonObject = JSON.parseObject(json);
           JSONObject response = jsonObject.getJSONObject("results");
           JSONArray orders = response.getJSONArray("n_tbk_order");
           List <Order> orderList = new ArrayList<Order>();
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
           }
           page_no++;
       }while (size>0);*/
        /*List <Order> orders = taobaoApiTest.getApiData(this.taobao_session);
        for(int i =0;i<orders.size();i++) {
            orderService.addOrder(orders.get(i));
        }*/
        out.println("后续提供动态授权服务");
        out.flush();
        out.close();
        return "index";
    }

    @RequestMapping("ajaxsetuniodid")
    public String getJduniodid(HttpServletResponse response,String unionid) throws ParseException, IOException {//获取京东联盟ID，暂不开启，后续写入数据库
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        /*String hasMore = "true";
        jdunionid = Integer.parseInt(unionid);
        String orderInfo = "";
        System.out.println("unionid为"+jdunionid);
        int pageIndex = 1;
        while (hasMore.equals("true")) {
            orderInfo = Util.jd_order(Jd_SERVER_URL, this.jd_Access_token, Jd_appKey, Jd_appSecret, jdunionid,pageIndex);
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
        }*/
        //for(int i=0;i<orders.size();i++){
           //System.out.println("订单号："+orders.get(i).getOrderId());
        //}

        //List<Order> orders = JdApiTest.getApiData(unionid,jd_Access_token);
        //for(int i =0;i<orders.size();i++) {
            //orderService.addOrder(orders.get(i));
        //}
        out.println("后续提供动态授权服务");
        out.flush();
        out.close();
        return "index";
    }

    @RequestMapping("jiankong")
    public String jiankong_Manager(){
        return "jiankong";
    }

    @RequestMapping("UserManager")
    public String UserManager(Model model){
        List<User> userList = userService.findAll();
        model.addAttribute("userList",userList);

        return "UserManager";
    }


    @RequestMapping("AddUserface")
    public String AddUserface(){
        return "AddUserface";
    }

    @RequestMapping("AddUserOperation")
    public String AddUserOperation(String account, String password){

        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setRole("0");

        userService.addUser(user);
        return "UserManager";
    }


    @RequestMapping("pdd_manager")
    public ModelAndView pdd_manager(HttpServletRequest request,Model model){

        User currentUser = (User)request.getSession().getAttribute("User_session");
        Pddautho pddautho = pddauthoService.selectByAccount(currentUser.getAccount());

        model.addAttribute("pddautho",pddautho);
        return new ModelAndView("pdd_manager","pddauthoModel",model);
    }

    @RequestMapping("jd_manager")
    public ModelAndView jd_manager(HttpServletRequest request,Model model){

        User currentUser = (User)request.getSession().getAttribute("User_session");
        Jdautho jdautho = jdauthoService.selectByUserAccount(currentUser.getAccount());

        model.addAttribute("jdautho",jdautho);
        return new ModelAndView("jd_manager","jdauthoModel",model);
    }

    @RequestMapping("tb_manager")
    public ModelAndView tb_manager(HttpServletRequest request,Model model){
        User currentUser = (User)request.getSession().getAttribute("User_session");
        Tbautho tbautho = tbauthoService.selectByaccount(currentUser.getAccount());

        model.addAttribute("tbautho",tbautho);
        return new ModelAndView("tb_manager","tbauthoModel",model);
    }

    @RequestMapping("PddTongbu")
    public String PddTongbu() throws ParseException {

        /*String PddOrderInfo = "";

        PddOrderInfo = Util.get_pddOrderApi(pdd_client_id,pdd_Access_token,pdd_client_secret);

        System.out.println("拼多多订单信息："+PddOrderInfo);*/

        /*List<Order> orders = PddApiTest.getApiData();
        for(int i=0;i<orders.size();i++){
            orderService.addOrder(orders.get(i));
        }*/
        return "index";
    }

}
