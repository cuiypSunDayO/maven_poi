package com.lenmo.testng.day02.Case;

import com.alibaba.fastjson.JSONPath;
import com.lenmo.testng.day02.Uitls.Constants;
import com.lenmo.testng.day02.Uitls.ExcelUtils;
import com.lenmo.testng.day02.Uitls.HttpUtils;
import com.lenmo.testng.day02.Uitls.SQLUtils;
import com.lenmo.testng.day02.pojo.ExcelDemo;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 充值接口测试类型
 */

public class RechargeCase extends BaseCase {

    @Test(dataProvider = "datas")
    public void f(ExcelDemo excelDemo) throws Exception {
        //执行注册接口测试逻辑
        //添加鉴权头
        //1.参数化替换
        paramsReplace(excelDemo);
        //2.数据库前置查询结果
       BigDecimal beforeSQLResult = (BigDecimal) SQLUtils.getSingleResult(excelDemo.getSql());
        //3鉴权头+默认头
        Map<String, String> authorizationHeader = getAuthorizationHeader();
        //调用注册接口
        String responseBody = HttpUtils.call(excelDemo, authorizationHeader);
        //4.断言响应结果 断言期望值和实际值匹配，匹配上了就是断言成功相反队验失败
        boolean responseAssertFlag = responseAssert(excelDemo.getExpectedResult(), responseBody);
        //5.添加响应回写内容
        addWriterBackData(sheetIndex, excelDemo.getId(), Constants.RESPONSE_CALL_NUM, responseBody);

        BigDecimal afterSQLResult = (BigDecimal) SQLUtils.getSingleResult(excelDemo.getSql());

        boolean  sqlAssertFlag= sqlAssert(excelDemo, beforeSQLResult, afterSQLResult);

        String assertResult = responseAssertFlag? "PASSED" : "FALIDE ";
        addWriterBackData(sheetIndex, excelDemo.getId(), Constants.ASSERT_CALL_NUM, assertResult);
//        9、添加日志
//        10、报表断言
        //10、报表断言 断言失败应该在报表中体现
        //testng收集信息->allure->展示
       // Assert.assertEquals(assertResult,);//pass
        Assert.assertEquals(assertResult,"PASSED");

    }

    public boolean sqlAssert(ExcelDemo excelDemo, BigDecimal beforeSQLResult, BigDecimal afterSQLResult) {
        boolean flag = false;
        if (StringUtils.isNotBlank(excelDemo.getSql())) {
            String amountStr = JSONPath.read(excelDemo.getParams(), "$amount").toString();
            BigDecimal amount = new BigDecimal(amountStr);
            BigDecimal subtractResult = afterSQLResult.subtract(beforeSQLResult);
            System.out.println("beforeSQLResult" + beforeSQLResult);
            System.out.println("afterSQLResult" + afterSQLResult);
            System.out.println("amount9999999999999" + amount);

            if (subtractResult.compareTo(amount) == 0) {
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
