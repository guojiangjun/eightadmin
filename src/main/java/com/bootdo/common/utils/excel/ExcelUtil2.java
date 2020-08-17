package com.bootdo.common.utils.excel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * Excel 工具类
 *
 * @author zhangyi
 * @version 1.0 2016/01/27
 *
 */
public class ExcelUtil2 {
    /**
     * Excel读取 操作
     */
    public static List<List<String>> readExcel( InputStream is ) {
        //System.out.println("原表数据 =====>" );
       // File file = new File(filePath);
      /*  String filename = file.getName();

        if (filename.endsWith("xls")) {
            wb = new HSSFWorkbook();
        } else if (filename.endsWith("xlsx")) {

        }*/
        Workbook wb =  wb = new XSSFWorkbook();
        try {
            //InputStream is = new FileInputStream(file.getAbsolutePath());
            wb = WorkbookFactory.create(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
            e.printStackTrace();
        }

        /** 得到第一个sheet */
        Sheet sheet = wb.getSheetAt(0);
        /** 得到Excel的行数 */
        int totalRows = sheet.getPhysicalNumberOfRows();

        //System.out.println("totalRows" + totalRows);

        /** 得到Excel的列数 */
        int totalCells = 0;
        if (totalRows >= 1 && sheet.getRow(0) != null) {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }

        //System.out.println("totalCells" + totalCells);
        List<List<String>> dataLst = new ArrayList<List<String>>();
        /** 循环Excel的行 */
        for (int r = 0; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null)
                continue;
            List<String> rowLst = new ArrayList<String>();
            /** 循环Excel的列 */
            for (int c = 0; c < totalCells; c++) {
                Cell cell = row.getCell(c);
                if(cell != null) {
                   // String value = cell.getCellType() == Cell.CELL_TYPE_FORMULA ? "" : cell.toString();
                    rowLst.add(getCellValue(cell));
                }
            }
            //System.out.println();
            /** 保存第r行的第c列 */
            dataLst.add(rowLst);
        }
        return dataLst;
    }
    public static  String getCellValue(Cell cell){
        //如果为null会抛出异常，应当返回空字符串
        if(cell == null){
            return "";
        }
        //解决日期03-五月-2018格式读入后的问题，POI读取后变成“02-十一月-2018”格式
        if (cell.toString().contains("-") && checkDate(cell.toString())) {
            //定义一个新的字符串
            String anString="";
            //设置日期格式
            anString =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
            anString=anString.toString();
            return anString;
        }
        cell.setCellType(Cell.CELL_TYPE_STRING);
        return cell.getStringCellValue();
    }
    /**
     * 判断是否是“02-十一月-2018”格式的日期类型
     */
    private static boolean checkDate(String str){
        String[] dataArr =str.split("-");
        try {
            if(dataArr.length == 3){
                int x = Integer.parseInt(dataArr[0]);
                String y =  dataArr[1];
                int z = Integer.parseInt(dataArr[2]);
                if(x>0 && x<32 && z>0 && z< 10000 && y.endsWith("月")){
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
