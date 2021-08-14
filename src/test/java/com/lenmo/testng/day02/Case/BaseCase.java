package com.lenmo.testng.day02.Case;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.lenmo.testng.day02.Uitls.ExcelUtils;
import com.lenmo.testng.day02.Uitls.HttpUtils;
import com.lenmo.testng.day02.Uitls.UserData;
import com.lenmo.testng.day02.pojo.ExcelDemo;
import com.lenmo.testng.day02.pojo.WriteBackData;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BaseCase {
    private static Logger logger =Logger.getLogger(BaseCase.class);

    //获取testng.xml中的sheetIndex
    public int sheetIndex;

    @BeforeClass
    @Parameters("sheetIndex")
    public void beforeClass(int sheetIndex){
        this.sheetIndex =sheetIndex;

    }
    @AfterSuite
    public void finish() throws Exception{
       // System.out.println("=============fish==============");
        logger.info("=============fish==============");
        ExcelUtils.batchWrite();
    }

    /**
     *
     * 从responseBoby通过jsonpath取出对应参数，存入到UserData.vars中
     * @param responseBody         json字符串
     * @param JsonPathExpression   jsonpath表达式
     * @param UserDataKey          vars中key
     */
    public void getParamsInUserData (String responseBody, String JsonPathExpression, String UserDataKey){

        Object userDataValue= JSONPath.read(responseBody, JsonPathExpression);
        System.out.println("UserDataKey" + UserDataKey);
        System.out.println("userDataValue" + userDataValue);
        if (userDataValue != null) {
            UserData.VARS.put(UserDataKey, userDataValue);

        //Object token = JSONPath.read(responseBody, JsonPathExpression);
        //存储到VAR中
     /*   if (token != null) {
            UserData.VARS.put(UserDataKey, token);*/
        }
       /* if (token != null) {
            UserData.VARS.put(UserDataKey, token);
        }*/
    }
    /**
     * 添加回写对象到回写集合中
     * @param sheetIndex
     * @param rowNum 行号
     * @param cellNum 列号

     * @param
     */
    public void addWriterBackData(int sheetIndex,int rowNum,int cellNum,String content) {
        WriteBackData wbd = new WriteBackData(sheetIndex, rowNum, cellNum, content);
        ExcelUtils.wbdList.add(wbd);
    }
    /**
     *
     *获取鉴权头，加入默认请求头，并且返回
     * @return
     * @throws Exception
     */
    public Map<String, String> getAuthorizationHeader( ) throws Exception {
      //  Object o = UserData.VARS.get("${token}");
        Object token= UserData.VARS.get("${token}");
        System.out.println(token);

        HashMap<String,String> headers =new HashMap<>();
        //鉴权头默认
        headers.put("Authorization","Bearer " + token);
        //headers.put("Authorization","JWT " + token);
        //添加默认头
        headers.putAll(UserData.DEFAULT_HEADERS);

        return headers;
    }

    /**
     * 接口响应断言
     * @param expectedResult     断言的期望值
     * @param responseBody       接口响应内容
     * @return                   接口响应断言结果
     */
    public boolean responseAssert(String expectedResult, String responseBody) {
        // String expectedResult = excelDemo.getExpectedResult();
        //转成一个map
        Map<String,Object > map = JSONObject.parseObject(expectedResult, Map.class);
        // 遍历map
        Set<String> keySet = map.keySet();
        boolean  responseAssertFlag = true;
        for (String actualValueExpression : keySet) {
            //获取了期望值
            Object  expectedValue = map.get(actualValueExpression);
            //通过表达式从响应体获取实际值
            Object actualValue = JSONPath.read(responseBody,actualValueExpression);
            //断言，只要失败一次，整个断言就全部失败，
            if(!expectedValue.equals(actualValue)){
                //断言失败
                responseAssertFlag =false;
                break;
            }
        }
        //System.out.println("接口断言结果："+ responseAssertFlag);
        logger.info("接口断言结果："+ responseAssertFlag);
        return responseAssertFlag;
    }

    public void paramsReplace(ExcelDemo excelDemo) {
        //现在只有占位符，没有真实的手机号码和密码
       //sql：select count(*) from member a where a.mobile_phone ='${register_mb}';
       //${register_mb}=13212312311 ${register_pwd}=12345678
       //params:{"mobile_phone":"${register_mb}}","pwd":"${register_pwd}"}
        String params = excelDemo.getParams();
        String sql = excelDemo.getSql();
        String expectedResult = excelDemo.getExpectedResult();
        String url = excelDemo.getUrl();
        Set<String> keySet = UserData.VARS.keySet();
        //取出所有的值key”“mobile_phone,"pwd",根据key找到value
        for (String placeHolder : keySet) {
            // Object value = UserData.VARS.get(placeHolder);
//            //因为值是字符串改成toString
//            //所有占位符的真实值value来替换加的占位符
            String value = UserData.VARS.get(placeHolder).toString();
            //找到替换的列
            if (StringUtils.isNotBlank(params)) {
                //旧的的占位符，替换成真的值。
                params = params.replace(placeHolder, value);
                excelDemo.setParams(params);
            }
            if (StringUtils.isNotBlank(sql)) {
                //实际值value替换占位符placeHolder
                sql = sql.replace(placeHolder, value);
                excelDemo.setSql(sql);
            }
            if (StringUtils.isNotBlank(expectedResult)) {
                //实际值value替换占位符placeHolder
                expectedResult = expectedResult.replace(placeHolder, value);
                excelDemo.setExpectedResult(expectedResult);
            }
            if (StringUtils.isNotBlank(url)) {
                //实际值value替换占位符placeHolder
                url = url.replace(placeHolder, value);
                excelDemo.setUrl(url);
            }
        }
        logger.info(excelDemo);




    }

    }


