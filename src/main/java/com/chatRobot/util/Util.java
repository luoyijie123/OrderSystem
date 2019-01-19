package com.chatRobot.util;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.request.cps.UnionServiceQueryImportOrdersRequest;
import com.jd.open.api.sdk.request.cps.UnionServiceQueryOrderListRequest;
import com.jd.open.api.sdk.response.cps.UnionServiceQueryImportOrdersResponse;
import com.jd.open.api.sdk.response.cps.UnionServiceQueryOrderListResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.json.JsonArray;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static javafx.scene.input.KeyCode.J;

public class Util {

    public static String loadJson(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL urlObject = new URL(url);
            URLConnection uc = urlObject.openConnection();
// 设置为utf-8的编码 才不会中文乱码
            BufferedReader in = new BufferedReader(new InputStreamReader(uc
                    .getInputStream(), "utf-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     * @param URL  url地址
     * @return  url请求参数部分
     */
    public static Map<String, String> URLRequest(String dealURL)
    {
        Map<String, String> mapRequest = new HashMap<String, String>();

        String[] arrSplit=null;

        String strUrlParam= dealURL;
        if(strUrlParam==null)
        {
            return mapRequest;
        }
        //每个键值为一组 www.2cto.com
        arrSplit=strUrlParam.split("[&]");
        for(String strSplit:arrSplit)
        {
            String[] arrSplitEqual=null;
            arrSplitEqual= strSplit.split("[=]");

            //解析出键值
            if(arrSplitEqual.length>1)
            {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            }
            else
            {
                if(arrSplitEqual[0]!="")
                {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }


    public static String jd_Json(String appkey,String appSecret,String redirect_uri,String code,String state){
        String url ="https://oauth.jd.com/oauth/token?grant_type=authorization_code&client_id="+appkey
                +"&client_secret="+ appSecret
                +"&scope=read&redirect_uri="+ redirect_uri
                +"&code="+ code
                +"&state="+state;
        URL uri = null;
        try {
            uri = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) uri.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.setRequestProperty("Accept-Charset","utf-8");
        try {
            conn.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        try {
            int code1 = conn.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream is = null;
        try {
            is = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonStr = InputStream2String(is);
        return jsonStr;
    }

    public static String pdd_Json(String code){
        String url="http://open-api.pinduoduo.com/oauth/token";
        JSONObject json=new JSONObject();
        try { json.put("client_id","1e3f5855199b47dd90e060343c690eef");
              json.put("client_secret", "6293f7d6a22cac64d87ae1d95b5ed71e5bf7d7dd");
              json.put("grant_type","authorization_code");
              json.put("code",code);
              json.put("redirect_uri","http://localhost:8080/ChatRobot/user/pinduoduoauth");
        } catch (JSONException e) {
            e.printStackTrace();
        }   String json1 = HttpUtil.loadJSON(url, json.toString());
        return  json1.toString();

    }

    public static String InputStream2String(InputStream in) {
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(in, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader br = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();
        String line = "";
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    //java解析json字符串详解,，获取京东的access_token
    public static String jd_Access_token(String JsonData){
        String Access_token="";
        JSONObject jsonObject = new JSONObject(JsonData);

            Access_token = jsonObject.getString("access_token");

        return Access_token;
    }

    //java解析json字符串详解,，获取拼多多的access_token
    public static String pingduoduo_Access_token(String JsonData){
        String Access_token="";
        JSONObject jsonObject = new JSONObject(JsonData);

        Access_token = jsonObject.getString("access_token");

        return Access_token;
    }

    public static String jd_order(String SERVER_URL,String accessToken,String appKey,String appSecret,int UnionId,int pageIndex){

        String result = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String time = df.format(new Date());//当前时间
        JdClient client=new DefaultJdClient(SERVER_URL,accessToken,appKey,appSecret);

        UnionServiceQueryOrderListRequest request=new UnionServiceQueryOrderListRequest();


        request.setUnionId( UnionId );
        request.setTime(time);
        request.setPageIndex(pageIndex);
        request.setPageSize( 500 );

        try {
            UnionServiceQueryOrderListResponse response=client.execute(request);
            result = response.getResult();

        } catch (JdException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String get_pddOrderApi(String client_id,String access_token,String client_secret,int page){//最近90天内多多进宝商品订单更新时间--查询时间开始。
        String data="";
        String type = "pdd.ddk.order.list.increment.get";
        String timestamp = TimeUtil.UnixTimestamp();

        String start_update_time = "1546790400";//2019-01-07 00:00:00 起始有订单的时间
        String end_update_time = "1546876799";//2019-01-07 23:59:59
        String data_type = "JSON";
        String version = "V1";
        Map<String,Object> parameter = new TreeMap<String, Object>();
        parameter.put("type",type);
        parameter.put("client_id",client_id);
        //parameter.put("access_token",access_token);
        parameter.put("timestamp",timestamp);
        parameter.put("data_type",data_type);
        //parameter.put("version",version);
        parameter.put("start_update_time",start_update_time);
        parameter.put("end_update_time",end_update_time);
        parameter.put("page_size","100");
        parameter.put("page",String.valueOf(page));

        String sign = SignUtils.getPddSign(parameter,client_secret);
        String url = "https://gw-api.pinduoduo.com/api/router";

        String param = "type="+type+"&"
                +"data_type=JSON&"
                +"timestamp="+timestamp+"&"
                +"client_id="+client_id+"&"
                +"start_update_time="+timestamp+"&"
                +"end_update_time="+timestamp+"&"
                +"page_size=10&page=1&"
                +"sign="+sign;

        JSONObject json=new JSONObject();
        try {
            json.put("type",type);
            json.put("data_type", "JSON");
            json.put("timestamp",timestamp);
            //json.put("access_token",access_token);
            json.put("client_id",client_id);
            json.put("start_update_time",start_update_time);
            json.put("end_update_time",end_update_time);
            json.put("page_size","100");
            json.put("page",String.valueOf(page));
            json.put("sign",sign);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json1 = HttpUtil.loadJSON(url, json.toString());
        return  json1.toString();

    }

    public static String get_pddSign(String client_secret,String type,String client_id,String access_token,String timestamp,String data_type,String version,String start_update_time,String end_update_time){
         Map <String,String> parameter = new TreeMap<String, String>();
         String temp="";
         String sign="";
         parameter.put("type",type);
         parameter.put("client_id",client_id);
         //parameter.put("access_token",access_token);
         parameter.put("timestamp",timestamp);
         parameter.put("data_type",data_type);
         parameter.put("version",version);
         parameter.put("start_update_time",start_update_time);
         parameter.put("end_update_time",end_update_time);
         parameter.put("page_size","10");
         parameter.put("page","1");
         Map<String,String> resMap = MapUtil.sortMapByKey(parameter);

         for(Map.Entry<String,String> entry:resMap.entrySet()){
             temp = temp+entry.getKey()+entry.getValue();
         }

         temp = client_secret+temp+client_secret;
         temp.replace(" ", "");
         sign = Md5Util.MD5(temp);
         System.out.println("签名的长度为："+sign.length());

         return sign;
    }

}
