package com.lenmo.testng.day02.Case;

import com.lenmo.testng.day02.Uitls.*;
import com.lenmo.testng.day02.pojo.ExcelDemo;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;


public class Register1 extends  BaseCase{

    @Test(dataProvider = "datas")
    public void f(ExcelDemo excelDemo) throws Exception {
        //1.参数化替换
        //SQL
        //参数
        paramsReplace(excelDemo);
        //2.数据库前置查询结果（数据断言必须在接口执行前后都查询）
        Long beforeSQLResult = (Long) SQLUtils.getSingleResult(excelDemo.getSql());
        //3.调用注册接口
        String responseBody = HttpUtils.call(excelDemo, UserData.DEFAULT_HEADERS);
        //4.断言响应结果 断言期望值和实际值匹配，匹配上了就是断言成功相反验证失败
        boolean responseAssertFlag = responseAssert(excelDemo.getExpectedResult(), responseBody);
        //5.添加响应回写内容
        addWriterBackData(sheetIndex, excelDemo.getId(), Constants.RESPONSE_CALL_NUM, responseBody);
        //6.数据库后置查询结果
        Long afterSQLResult = (Long) SQLUtils.getSingleResult(excelDemo.getSql());
        //7.数据库断言
        //如果sql数据库为空不用断言
        boolean sqlAssertFlag = sqlAssert(excelDemo.getSql(), beforeSQLResult, afterSQLResult);
       // 8.添加断言回写内容
        String assertResult = responseAssertFlag&sqlAssertFlag ? "PASSED" : "FALIDE ";
        addWriterBackData(sheetIndex, excelDemo.getId(), Constants.ASSERT_CALL_NUM, assertResult);



    }






    public boolean sqlAssert(String sql, Long beforeSQLResult, Long afterSQLResult) {
       boolean flag = false;
        if (StringUtils.isNotBlank(sql)) {
            System.out.println("beforeSQLResult" + beforeSQLResult);
            System.out.println("afterSQLResult" + afterSQLResult);
            if (beforeSQLResult == 0 && afterSQLResult == 1) {
                System.out.println("数据库断言成功");
                flag =true;

            } else {
                System.out.println("数据库断言失败");
            }

        }
       return  flag;
    }

    @DataProvider
    public Object[] datas() throws Exception {
        List<ExcelDemo> list = ExcelUtils.read(sheetIndex, 1, ExcelDemo.class);
        return list.toArray();
    }
}
