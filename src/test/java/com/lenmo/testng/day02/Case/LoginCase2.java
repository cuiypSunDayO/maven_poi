package com.lenmo.testng.day02.Case;

import com.lenmo.testng.day02.Uitls.Constants;
import com.lenmo.testng.day02.Uitls.ExcelUtils;
import com.lenmo.testng.day02.Uitls.HttpUtils;
import com.lenmo.testng.day02.Uitls.UserData;
import com.lenmo.testng.day02.pojo.ExcelDemo;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;


public class LoginCase2 extends BaseCase {

    @Test(dataProvider = "datas")
    public void f(ExcelDemo excelDemo) throws Exception {
        //执行注册接口测试逻辑
        //调用注册接口
        paramsReplace(excelDemo);
        //3鉴权头+默认头
//        Map<String, String> authorizationHeader = getAuthorizationHeader();
//        //调用注册接口
//        String responseBody = HttpUtils.call(excelDemo, authorizationHeader);
        String responseBody = HttpUtils.call(excelDemo, UserData.DEFAULT_HEADERS);
        //使用JsonPath从响应体中取出token，memberId
        getParamsInUserData(responseBody,"$.token","${token}");
        getParamsInUserData(responseBody,"$.user_id","${user_id}");
       // getParamsInUserData(responseBody,"$.username","${username}");
       //4.断言响应结果，断言期望值和实际值匹配，匹配上了就是断言成功，相反断言失败。
        //因为响应是json格式
        //{"获取实际值的表情市JsonPath":期望值,"$.msg":"OK"}

        boolean responseAssertFlag = responseAssert(excelDemo.getExpectedResult(), responseBody);
        //5.添加响应回写内容
        addWriterBackData(sheetIndex, excelDemo.getId(), Constants.RESPONSE_CALL_NUM, responseBody);
        //6.数据库后置查询结果
        //7.数据库断言
        //8.添加断言回写内容
        String assertResult = responseAssertFlag ? "PASSED" : "FALIDE ";
        addWriterBackData(sheetIndex, excelDemo.getId(), Constants.ASSERT_CALL_NUM, assertResult);
        //9.添加日志 日志：①定位框架的错误，②输出统计

        //10.报表断言
    }



    @DataProvider
    public Object[] datas() throws Exception {
        List<ExcelDemo> list = ExcelUtils.read(sheetIndex, 1, ExcelDemo.class);
        return list.toArray();
    }
}
