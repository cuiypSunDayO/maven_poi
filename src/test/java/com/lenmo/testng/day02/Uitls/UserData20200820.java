package com.lenmo.testng.day02.Uitls;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 崔崔
 * @date 2020/8/20-17:21
 * 小崔hello
 */
public class UserData20200820 {
    //储存接口响应变量
    public static Map<String,Object> VARS =new HashMap<>();
    public static Map<String,String> DEFAULT_HEADERS =new HashMap<>();
    static{
        DEFAULT_HEADERS.put("Content-Type","application/json");
    }

}

