package com.lenmo.testng.day02.pojo;

import com.alibaba.fastjson.JSONPath;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author 崔崔
 * @date 2020/8/20-14:12
 * 小崔hello
 */
public class HttpUtils20200820 {
    public static void post() throws Exception {
        //创建链接对象，加入url
        HttpPost post = new HttpPost("http://api.keyou.site:8000/user/login/");
        //设置请求头
        post.setHeader("Content-Type","application/json");
        //设置请求体
        StringEntity boby = new StringEntity("{\"username\":\"cuiyongping1101\",\"password\":\"12345678\",\"email\":\"softcui1101@qq.com\",\"password_confirm\":\"12345678\"}","utf-8");
        post.setEntity(boby);
        //创建客户端对象
        CloseableHttpClient client = HttpClients.createDefault();
        //发送请求
        CloseableHttpResponse response = client.execute(post);
        //从响应中拿到状态码
        int code = response.getStatusLine().getStatusCode();
        System.out.println(code);
        //请求头
        Header[] headers = response.getAllHeaders();
        System.out.println(headers);
        //请求实体
        HttpEntity entity1 = response.getEntity();
        String body = EntityUtils.toString(entity1);
        System.out.println(body);

        Object token = JSONPath.read("$.token","${token}" );
        System.out.println(token);
//        String token1=null;
//        UserData20200820.VARS.put(token1,token);
//        Object o = UserData20200820.VARS.get(token1);



    }
    public static void get() throws Exception {
       // post();
        CloseableHttpClient client = HttpClients.createDefault();
        URI uri =new URIBuilder()
                .setHost("api.keyou.site")
                .setPort(8000)
                .setPath("/configures/")
                .setParameter("page","1")
                .setParameter("size","1")
                .setParameter("ordering","1")
                .build();
        HttpGet get = new HttpGet(uri);
        get.setHeader("Content-Type","application/json");
        get.setHeader("Authorization","JWT ${token}");
        //发送请求
        CloseableHttpResponse response = client.execute(get);
        //从响应中拿到状态码
        int code = response.getStatusLine().getStatusCode();
        System.out.println(code);
        //请求头
        Header[] headers = response.getAllHeaders();
        System.out.println(headers);
        //请求实体
        HttpEntity entity1 = response.getEntity();
        String body = EntityUtils.toString(entity1);
        System.out.println(body);

    }




}
