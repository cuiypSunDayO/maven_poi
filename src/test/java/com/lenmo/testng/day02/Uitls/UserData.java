package com.lenmo.testng.day02.Uitls;

import cn.binarywang.tools.generator.ChineseMobileNumberGenerator;
import cn.binarywang.tools.generator.ChineseNameGenerator;
import cn.binarywang.tools.generator.EmailAddressGenerator;

import java.util.HashMap;
import java.util.Map;

public class UserData {
    public static Map<String ,Object> VARS =new HashMap<>();

    public static Map<String ,String> DEFAULT_HEADERS=new HashMap<>();

    static {
        //静态代码块,类在加载时自动就会运行此代码
        DEFAULT_HEADERS.put("X-Lemonban-Media-Type","lemonban.v2");
        DEFAULT_HEADERS.put( "Content-Type","application/json");

        VARS.put("${register_mb}", ChineseMobileNumberGenerator.getInstance().generate());
        VARS.put("${register_pwd}", "12345678");
        VARS.put("${email}", EmailAddressGenerator.getInstance().generate());
        VARS.put("${name}", ChineseNameGenerator.getInstance().generateOdd());

       // VARS.put("${amount}", "5000");
    }
}

