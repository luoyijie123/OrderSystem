package com.chatRobot.util;



import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.*;


public class HttpUtil {

    public static String Post(String Url,String param){
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(Url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Charsert", "UTF-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String loadJSON (String url,String param) {

        StringBuilder json = new StringBuilder();

        PrintWriter out = null;

        try {

            // Post请求的url，与get不同的是不需要带参数

            URL oracle = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) oracle.openConnection();

            // 发送POST请求必须设置如下两行

            connection.setDoOutput(true);

            connection.setDoInput(true);

            connection.setUseCaches(false);

            connection.setInstanceFollowRedirects(true);

            connection.setRequestMethod("POST"); // 设置请求方式

            connection.setRequestProperty("Content-Type", "application/json"); // 设置接收数据的格式

            connection.connect();



            out = new PrintWriter(connection.getOutputStream());

            out.print(param);

            // flush输出流的缓冲

            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(

                    connection.getInputStream(),"utf-8"));



            String inputLine = null;

            while ( (inputLine = in.readLine()) != null) {

                json.append(inputLine);

            }

            in.close();

        } catch (IOException e) {

            System.out.println("发送 POST 请求出现异常！" + e);

            return "-1";

        }

        return json.toString();

    }

    }