package com.lenmo.testng.day02.Uitls;

/**
 * @author 崔崔
 * @date 2020/8/17-17:21
 * 小崔hello
 */
public class Constants {
    //final修饰变量，变量成为常量，常量只能赋值一次。
    //响应数据回写列号
    public static final int RESPONSE_CALL_NUM = 8;
    //断言回写列号
    public static final int ASSERT_CALL_NUM = 10;
    //用例文件地址
    public static final String  EXCEL_PATH ="src/test/resources/cases_v3.xlsx";
    public static final    String JDBC_URL ="jdbc:mysql://api.lemonban.com:3306/futureloan?useUnicode=true&characterEncoding=utf-8";
    public static final    String JDBC_USERNAME ="future";
    public static final    String JDBC_PASSWORD ="123456";
}
