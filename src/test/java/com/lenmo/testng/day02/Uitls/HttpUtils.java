package com.lenmo.testng.day02.Uitls;


import com.alibaba.fastjson.JSONObject;
import com.lenmo.testng.day02.pojo.ExcelDemo;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class HttpUtils {
    private static Logger logger =Logger.getLogger(HttpUtils.class);
    /**
     * 发送一个get请求
     * @param
     * @throws Exception
     */
    public static String call(ExcelDemo excelDemo,Map<String,String> headers) {
        String responseBody = "";
        try {
            String params = excelDemo.getParams();
            String url = excelDemo.getUrl();
            String method = excelDemo.getMethod();
            //2、判断请求方式，如果是post
            if("post".equalsIgnoreCase(method)) {
                String contentType = excelDemo.getContentType();
                //2.1、判断参数类型，如果是json
                if("json".equalsIgnoreCase(contentType)) {
                    //2.2、判断参数类型，如果是form

                }else if("form".equalsIgnoreCase(contentType)) {
                    //json参数转成key=value参数
                    params = jsonStr2KeyValueStr(params);
                    System.out.println("formParams:" + params);
                    //覆盖默认请求头中的Content-Type
                    headers.put("Content-Type","application/x-www-form-urlencoded");
                }
                responseBody = HttpUtils.post(url,params,headers);
                //3、判断请求方式，如果是get
            }else if("get".equalsIgnoreCase(method)) {
                responseBody = HttpUtils.get(url,headers);
                //4、判断请求方式，如果是patch
            }else if("patch".equalsIgnoreCase(method)){
                responseBody = HttpUtils.patch(url,params,headers);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return responseBody;
    }
   /* public static String call(ExcelDemo excelDemo,Map<String,String> headers) throws Exception {
        String responseBody = "";

        // 1.创建默认请求头对象，并且添加X-lemonban-Media-Type
       // HashMap<String, String> headers = new HashMap<>();
        //headers.put("X-Lemonban-Media-Type", "lemonban.v2");
        String params = excelDemo.getParams();
        String url = excelDemo.getUrl();
        String method = excelDemo.getMethod();
        //2.判断请求方式，如果是post                                                  //
        if ("post".equalsIgnoreCase(method)) {
            String contentType = excelDemo.getContentType();

            if ("json".equals(contentType)) {
               // headers.put("Content-Type", "application/json");
            } else if ("form".equals(contentType)) {
                params = jsonStr2KeyValueStr(params);
                System.out.println("formParams:" + params);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
            }
            responseBody =HttpUtils.post(url, params, headers);


        } else if ("get".equalsIgnoreCase(method)) {
            responseBody = HttpUtils.get(url,headers);
        } else if ("patch".equalsIgnoreCase(method)) {
           // headers.put("Content-Type", "application/json");
            responseBody = HttpUtils.patch(url, params,headers);
        }

        return  responseBody;
        //执行注册接口测试逻辑
        //调用注册接口
    }*/
    /**
     * json字符串转成key=value
     * 例如：{"mobilephone":"13877788811","pwd":"12345678"} = > mobilephone=13877788811&pwd=12345678
     * @param json      JSON字符串
     * @return
     */
    public static String jsonStr2KeyValueStr(String json){
        Map<String,String> map = JSONObject.parseObject(json,Map.class);
        Set<String> keySet = map.keySet();
        String formParams = "";
        for (String key : keySet) {
            //key=value&key=value&key=value&
            String value = map.get(key);
            formParams +=key + "=" + value + "&";
        }
       return  formParams.substring(0, formParams.length()-1);
    }

    /*public static String get(String url,Map<String,String> headers) throws Exception {
        //创建链接对象，加入url
        HttpGet get =new HttpGet(url);
        //设置请求头
        setHeaders(headers, get);
        //创建客户端对象
        CloseableHttpClient client = HttpClients.createDefault();
        //发送请求
        CloseableHttpResponse response = client.execute(get);

        return  printResponse(response);
    }*/
    public static String get(String url, Map<String,String> headers) throws Exception {
        //1、创建请求
        HttpGet get = new HttpGet(url);
        //2、添加请求头
        setHeaders(headers, get);
        //3、创建客户端
        HttpClient client = HttpClients.createDefault();
        //4、发送请求，获取响应对象
        HttpResponse response = client.execute(get);
        //5、格式化响应对象 response = 响应状态码 + 响应头 + 响应体
        return printResponse(response);
    }

 /*   public static String post(String url, String params, Map<String ,String> headers) throws Exception {
        //创建链接对象，加入url
        HttpPost post =new HttpPost(url);
        //设置请求头
        setHeaders(headers, post);
//        post.setHeader("X-Lemonban-Media-Type","lemonban.v1");
//        post.setHeader("Content-Type","application/json");


        //设置请求体2065002   "{\"member_id\":\"2065002\",\"amount\":\"1\"}"
        StringEntity boby = new StringEntity(params,"utf-8");
        post.setEntity(boby);

        //创建客户端对象
      CloseableHttpClient client = HttpClients.createDefault();

        //发送请求
        CloseableHttpResponse response = client.execute(post);

       return printResponse(response);
    }*/
 public static String post(String url, String params, Map<String,String> headers) throws Exception {
     //1、创建请求
     HttpPost post = new HttpPost(url);
     //2、添加请求头
//        post.setHeader("X-Lemonban-Media-Type","lemonban.v1");
//        post.setHeader("Content-Type","application/json");
//        post.setHeader("Content-Type","application/x-www-form-urlencoded");
     //headers.put("X-Lemonban-Media-Type","lemonban.v1");
     //headers.put("Content-Type","application/json");
     setHeaders(headers, post);
     // setHeaders 执行的代码就是 post.setHeader
//        post.setHeader("X-Lemonban-Media-Type","lemonban.v1");
//        post.setHeader("Content-Type","application/json");
     //3、添加请求体（参数）
     StringEntity body = new StringEntity(params,"utf-8");
     post.setEntity(body);
     //4、创建客户端
     HttpClient client = HttpClients.createDefault();
     //5、发送请求，获取响应对象
     HttpResponse response = client.execute(post);
     //6、格式化响应对象 response = 响应状态码 + 响应头 + 响应体
     return printResponse(response);
 }

    public static String patch(String url,String params,Map<String ,String> headers) throws Exception {
        //创建链接对象，加入url
        HttpPatch patch = new HttpPatch(url);
        //设置请求头
        setHeaders(headers,patch);
//        patch.setHeader("X-Lemonban-Media-Type","lemonban.v1");
//        patch.setHeader("Content-Type","application/json");
        //设置请求体2065002
        StringEntity boby = new StringEntity(params,"utf-8");
        patch.setEntity(boby);

        //创建客户端对象
        HttpClient client = HttpClients.createDefault();

        //发送请求
        HttpResponse response = client.execute(patch);


        return  printResponse(response);

    }
    public static String printResponse(HttpResponse response) throws Exception {

        //状态码
        int statusCode = response.getStatusLine().getStatusCode();
        logger.info(statusCode);
       // System.out.println(statusCode);
        //请求头
        Header[] allHeaders = response.getAllHeaders();
        logger.info(Arrays.toString(allHeaders));
        //System.out.println(Arrays.toString(allHeaders));
        //请求实体
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        logger.info(body);
        logger.info("======================================================");
        return body;
    }

//    public  static void setHeaders(Map<String ,String> headers,HttpRequest request){
//        //获取所有请求头name
//        Set<String> keySet = headers.keySet();
//        //遍历所有的请求头name
//        for (String key : keySet) {
//            //获取请求头的name对应的value
//            String value =headers.get(key);
//            request.setHeader(key, value );
//        }
//    }
public static void setHeaders(Map<String, String> headers, HttpRequest request) {
    //获取所有请求头name
    Set<String> headerNames = headers.keySet();
    //遍历所有的请求头name
    for (String headerName : headerNames) {
        //获取请求头name对应的value
        String headerValue = headers.get(headerName);
        //设置请求头name，value
        request.setHeader(headerName,headerValue);
    }
}

}
