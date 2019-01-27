package com.dawnyu.maple.test;

import com.alibaba.fastjson.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

public class HttpRequest {



    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl
     *            请求地址
     * @param requestMethod
     *            请求方式（GET、POST）
     * @param outputStr
     *            提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        System.out.println("==============进入httpRequest方法===============");

        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            //URL url = new URL(requestUrl);
            //URL url = new URL(null,requestUrl,new com.sun.net.ssl.internal.www.protocol.https.Handler());
            URL url = new URL(null,requestUrl,new sun.net.www.protocol.https.Handler());
            System.out.println("==============发送的url"+url+"===============");
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            System.out.println("============== url.openConnection()"+httpUrlConn+"===============");

            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)){
                httpUrlConn.connect();
            }

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            System.out.println("============== buffer.toString()"+buffer.toString()+"===============");
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
            System.out.println("============== jsonObject"+jsonObject+"===============");
        } catch (ConnectException ce) {
            //log.error("Weixin server connection timed out.");
            System.out.println("============== Weixin server connection timed out."+ce+"===============");
        } catch (Exception e) {
            //log.error("https request error:{}", e);
            System.out.println("==============https request error:{}"+e+"===============");
        }

        System.out.println("==============httpRequest方法结束===============");
        return jsonObject;
    }

    public static void main(String[] args) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxee9ab440b6840cc4&secret=cea08fed802c6ae148edd9737c655c90&code=081ojmtR0gkDL62S8MrR0lWftR0ojmtl&grant_type=authorization_code";
        JSONObject jsonObject = httpRequest(url,"GET",null);
        System.out.println(jsonObject.toJSONString());
    }

}
