package com.lenmo.testng.day02.Uitls;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.lenmo.testng.day02.pojo.WriteBackData;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {
    //批量回写存储List集合
    public static List<WriteBackData> wbdList  =new ArrayList<>();

    public static List read(int sheetNum,int sheetIndex,Class clazz) throws Exception {
        //1.excel文件流
        FileInputStream fis = new FileInputStream(Constants.EXCEL_PATH);
        //2.easypoi导入参数
        ImportParams params = new ImportParams();
        //从第0个sheet开始读取
        params.setStartSheetIndex(sheetNum);
        //读取1个sheet
        params.setSheetNum(sheetIndex);
        //3导入importExcel(文件流，映射关系字节码对象，导入参数)
        List excelDemoList = ExcelImportUtil.importExcel(fis, clazz, params);
        //4.关流
        fis.close();
      /*  for (ExcelDemo excelDemo : excelDemoList) {
            System.out.println(excelDemo);
        }*/
        return excelDemoList;

    }

    /**
     * 批量回写
     */
    public static void batchWrite() throws Exception {
        //回写的程序，创建一个回写内容，
        FileInputStream fis = new FileInputStream(Constants.EXCEL_PATH);
        //获取所有的sheet
        Workbook sheets = WorkbookFactory.create(fis);
        //循环wbdList集合
        for (WriteBackData wbd: wbdList) {
            int sheetIndex=wbd.getSheetIndex();
            int rowNum=wbd.getRowNum();
            int cellNum=wbd.getCellNum();
            String content=wbd.getContent();
            //获取对应的Sheet对象
            Sheet sheet = sheets.getSheetAt(sheetIndex);
            //获取对应的Row对象
            Row row = sheet.getRow(rowNum);
            //获取对应cell对象
            Cell cell = row.getCell(cellNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            //回写内容
            cell.setCellValue(content);
        }
        //回写到excel文件中
            FileOutputStream fos =new FileOutputStream(Constants.EXCEL_PATH);
            sheets.write(fos);
            fis.close();
            fos.close();
    }
}
