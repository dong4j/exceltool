package com.dong4j.utils;

import com.dong4j.entity.AnalysisEntity;
import com.dong4j.entity.ExcelEntity;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

/**
 * Created by: dong4j.
 * Date: 2016-12-26.
 * Time: 22:19.
 * Description:
 */
public class ExcelTool {
    public static Workbook getWorkbook(File fileName){
        boolean                isE2007   = false;    //判断是否是excel2007格式
        if (fileName.getName().endsWith("xlsx"))
            isE2007 = true;
        Workbook    wb    = null;
        try {
            InputStream input = new FileInputStream(fileName);  //建立输入流
            //根据文件格式(2003或者2007)来初始化
            wb = isE2007 ? new XSSFWorkbook(input) : new HSSFWorkbook(input);
        }catch (IOException e){
            e.printStackTrace();
        }
        return wb;
    }
    /**
     * 解析表单
     * @param wb
     * @return
     */
    public static List<String> analysisSheet(Workbook wb) {
        List<String> sheetList = new ArrayList<>();
        try {
            Sheet sheet = null;
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {//获取每个Sheet表
                // 1.获取表单
                sheet = wb.getSheetAt(i);
                System.out.println(sheet.getSheetName());
                sheetList.add("第" + (i+1)  + "个表单: " + sheet.getSheetName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sheetList;
    }

    /**
     * 解析每一行
     * @param sheetIndex
     * @return
     */
    public static List<Object> analysisRow(Workbook wb, int sheetIndex){
        List<Object> list = new ArrayList<>();
        // getPhysicalNumberOfRows()获取的是物理行数，也就是不包括那些空行（隔行）的情况。
        // getLastRowNum()获取的是最后一行的编号（编号从0开始）
        // 1. 遍历某行数据,返回 list,只获取5行
        Sheet sheet = wb.getSheetAt(sheetIndex);
        for(int i = 0; i < sheet.getPhysicalNumberOfRows(); i++){
            if(i == 10){
                break;
            }
            Row row = sheet.getRow(i);
            // 默认取第二列的数据
            if(row != null){
                int cellIndex =  1;
                boolean isMerge = isMergedRegion(sheet, i, cellIndex);
                //判断是否具有合并单元格
                if (!isMerge) {
                    //System.out.print(getCellValue(row.getCell(cellIndex)) + "\t");
                    list.add("第" + (i+1) + "行: " + getCellValue(row.getCell(cellIndex)));
                }else{
                    // 如果是合并过的单元格,则不处理
                    //System.out.print("合并过的单元格" + "\t");
                    list.add("第" + (i+1)  + "行: " + "没有数据");
                }
            }
        }

        // 遍历所有数据
        //for (int j = 0; j < sheet.getLastRowNum(); j ++) { //获取每行
        //    Row row = sheet.getRow(j);
        //    for (int k = 0; k < row.getLastCellNum(); k ++) {//获取每个单元格
        //        //System.out.print(row.getCell(k) + "\t");
        //        boolean isMerge = isMergedRegion(sheet, j, row.getCell(k).getColumnIndex());
        //        //判断是否具有合并单元格
        //        if (!isMerge) {
        //            System.out.print(getCellValue(row.getCell(k)) + "\t");
        //            //String rs = getMergedRegionValue(sheet, j, row.getCell(k).getColumnIndex());
        //            //System.out.print(rs + " ");
        //        }else{
        //            // 如果是合并过的单元格,则不处理
        //            System.out.print("合并过的单元格" + "\t");
        //        }
        //    }
        //    System.out.println("第" + j + "行处理完毕---");
        //}
        return list;
    }

    /**
     * 解析每一列
     * @param sheetIndex
     * @return
     */
    public static List<Object> analysisCell(Workbook wb, int sheetIndex, int rowIndex){
        List<Object> list = new ArrayList<>();
        // getPhysicalNumberOfRows()获取的是物理行数，也就是不包括那些空行（隔行）的情况。
        // getLastRowNum()获取的是最后一行的编号（编号从0开始）
        // 1. 遍历某行数据,返回 list,只获取5行
        Sheet sheet = wb.getSheetAt(sheetIndex);
        Row row = sheet.getRow(rowIndex);
        for (int k = 0; k < row.getPhysicalNumberOfCells(); k++) {//获取每个单元格
            //System.out.print(row.getCell(k) + "\t");
            //row.getCell(k).getColumnIndex();
            boolean isMerge = isMergedRegion(sheet, rowIndex, k);
            //判断是否具有合并单元格
            if (!isMerge) {
                System.out.print("第" + (k+1) + "列: " + getCellValue(row.getCell(k)) + "\t");
                list.add("第" + (k+1) + "列: " + getCellValue(row.getCell(k)));
                //String rs = getMergedRegionValue(sheet, j, row.getCell(k).getColumnIndex());
                //System.out.print(rs + " ");
            }else{
                // 如果是合并过的单元格,则不处理
                System.out.print("第" + (k+1) + "列: " + "合并过的单元格" + "\t");
                list.add("第" + (k + 1) + "列: " + "没有数据");
            }
        }
        return list;
    }


    /**
     * 获取合并单元格的值
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public static String getMergedRegionValue(Sheet sheet ,int row , int column){
        int sheetMergeCount = sheet.getNumMergedRegions();

        for(int i = 0 ; i < sheetMergeCount ; i++){
            CellRangeAddress ca          = sheet.getMergedRegion(i);
            int              firstColumn = ca.getFirstColumn();
            int              lastColumn  = ca.getLastColumn();
            int              firstRow    = ca.getFirstRow();
            int              lastRow     = ca.getLastRow();

            if(row >= firstRow && row <= lastRow){

                if(column >= firstColumn && column <= lastColumn){
                    Row  fRow  = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell) ;
                }
            }
        }

        return null ;
    }

    /**
     * 判断合并了行
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public static boolean isMergedRow(Sheet sheet,int row ,int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row == firstRow && row == lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断指定的单元格是否是合并单元格
     * @param sheet
     * @param row 行下标
     * @param column 列下标
     * @return
     */
    public static boolean isMergedRegion(Sheet sheet,int row ,int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断sheet页中是否含有合并单元格
     * @param sheet
     * @return
     */
    public static boolean hasMerged(Sheet sheet) {
        return sheet.getNumMergedRegions() > 0;
    }

    /**
     * 获取单元格的值
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell){
        if(cell == null) return "";
        if(cell.getCellType() == Cell.CELL_TYPE_STRING){
            return cell.getStringCellValue();
        }else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
            return String.valueOf(cell.getBooleanCellValue());
        }else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){
            return cell.getCellFormula() ;
        }else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            return String.valueOf(cell.getNumericCellValue());
        }
        return "";
    }

    /**
     * 合并单元格
     * @param sheet
     * @param firstRow 开始行
     * @param lastRow 结束行
     * @param firstCol 开始列
     * @param lastCol 结束列
     */
    public static void mergeRegion(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }


    /**
     * 获取指定文件,指定表单,指定行,指定列的数据
     * @param fileName
     * @param sheetIndex
     * @param rowNum
     * @param column
     * @return
     */
    public static List<Object> analysis(File fileName, int sheetIndex, // 第几个表单
                                                   int  rowNum,  // 第几行
                                                   int column){ // 第几列
        AnalysisEntity analysisEntity = null;
        List<Object> list    = new ArrayList<>();
        boolean                isE2007 = false;    //判断是否是excel2007格式
        if (fileName.getName().endsWith("xlsx"))
            isE2007 = true;
        try {
            InputStream input = new FileInputStream(fileName);  //建立输入流
            Workbook    wb    = null;
            //根据文件格式(2003或者2007)来初始化
            if(isE2007)
                wb = new XSSFWorkbook(input);
            else
                wb = new HSSFWorkbook(input);
            Sheet         sheet = wb.getSheetAt(sheetIndex);     //获得第几个表单
            Iterator<Row> rows  = sheet.rowIterator(); //获得第一个表单的迭代器
            while (rows.hasNext()) { // 遍历第几行
                Row row = rows.next();  //获得行数据
                //System.out.println("Row #" + row.getRowNum());  //获得行号从0开始
                Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    //System.out.println("Cell #" + cell.getColumnIndex());
                    if (cell.getColumnIndex() == column   // 遍历第几列
                            && row.getRowNum() >= rowNum ) {
                        analysisEntity = new AnalysisEntity();
                        analysisEntity.setSheetIndex(sheetIndex);
                        analysisEntity.setRowNum(row.getRowNum());
                        analysisEntity.setColumn(cell.getColumnIndex());
                        switch (cell.getCellType()) {   //根据cell中的类型来输出数据
                            case HSSFCell.CELL_TYPE_NUMERIC:
                                //System.out.println(cell.getNumericCellValue());
                                analysisEntity.setData(cell.getNumericCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_STRING:
                                //System.out.println(cell.getStringCellValue());
                                analysisEntity.setData(cell.getStringCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN:
                                //System.out.println(cell.getBooleanCellValue());
                                analysisEntity.setData(cell.getBooleanCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_FORMULA:
                                //System.out.println(cell.getCellFormula());
                                analysisEntity.setData(cell.getCellFormula());
                                break;
                            default:
                                //System.out.println("unsuported sell type");
                                analysisEntity.setData("");
                                break;
                        }
                        //System.out.println(cell.getStringCellValue());
                        list.add(analysisEntity);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    /**
     * 求ls对ls2的差集,
     * 即ls中有，但ls2中没有的
     * @param ls
     * @param ls2
     * @return
     */
    public static List<Object> diff(List<Object> ls, List<Object> ls2) {
        if(ls.size() < ls2.size()){
            List<Object> temp = ls;
            ls = ls2;
            ls2 = temp;
        }
        List<Object> list = new ArrayList<>(Arrays.asList(new Object[ls.size()]));
        Collections.copy(list, ls);
        list.removeAll(ls2);
        return list;
    }

    /**
     * 求2个集合的交集
     * 2个集合中都有的
     * @param ls
     * @param ls2
     * @return
     */
    public static List<Object> intersect(List<Object> ls, List<Object> ls2) {
        List<Object> list = new ArrayList<>(Arrays.asList(new String[ls.size()]));
        Collections.copy(list, ls);
        list.retainAll(ls2);
        return list;
    }

    /**
     * 求2个集合的并集
     *
     * @param ls
     * @param ls2
     * @return
     */
    public static List<Object> union(List<Object> ls, List<Object> ls2) {
        List<Object> list = new ArrayList<>(Arrays.asList(new Object[ls.size()]));
        Collections.copy(list, ls);//将ls的值拷贝一份到list中
        list.removeAll(ls2);
        list.addAll(ls2);
        return list;
    }

    /**
     * 创建Excel表格
     *
     * @param object
     * @param outFilePath
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static void exportExcel(ExcelEntity object, String outFilePath) {
        HSSFWorkbook wb    = new HSSFWorkbook();
        HSSFSheet    sheet = wb.createSheet(object.getSheetName());
        HSSFRow      row   = sheet.createRow(0);// 创建第一行
        HSSFCell     cell  = row.createCell(0);// 创建第一行的第一个单元格
        cell.setCellValue("序号");
        String[] colNames = object.getColumnNames();
        String[] propertys = object.getPropertyNames();
        for (int i = 0; i < colNames.length; i++) { // 添加列名，从第一行的第二个单元格开始添加
            row.createCell(i + 1).setCellValue(colNames[i]);
        }
        Iterator it = object.getResultList().iterator();
        int rowNum = 1; // 从第二行开始添加数据
        while (it.hasNext()) {
            Map map = (Map) it.next();
            HSSFRow rw = sheet.createRow(rowNum);
            rw.createCell(0).setCellValue(rowNum); // 添加序号
            rowNum++;
            for (int x = 0; x < propertys.length; x++) {
                String property = propertys[x];
                if (map.containsKey(property)) {
                    Object value = map.get(propertys[x]); // 根据属性名称得到属性值
                    if (value == null || "null".equalsIgnoreCase(value.toString())) {
                        value = "";
                    }
                    rw.createCell(x + 1).setCellValue(value + "");
                } else {
                    rw.createCell(x + 1).setCellValue("");
                }
            }
        }
        // 第六步，将文件存到指定位置
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(outFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            wb.write(fout);
            if (fout != null) {
                fout.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove duplicate with order list.
     * 去掉List 中重复的对象并保持顺序
     * @param list the list
     * @return the list
     */
    public static List<Object> removeDuplicateWithOrder(List<Object> list) {
        Set<Object> set = new HashSet<>();
        List<Object> newList = new ArrayList<>();
        for (Object element : list) {
            if (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
        return list;
    }

    public static void main(String[] args){
        List<Object> s1 = new ArrayList<>();
        s1.add("1");
        s1.add("1");
        s1.add("1");
        s1.add("2");
        s1.add("3");
        s1.add("4");

        List<Object> s2 = new ArrayList<>();
        s2.add("1");
        s2.add("1");
        s2.add("2");
        s2.add("3");
        List<Object> result = diff(s2, s1); // 差集
        System.out.println("差集");
        for(Object o : result){
            System.out.println(o);
        }

        //List<Object> result1 = intersect(s1, s2); // 交集
        //System.out.println("交集");
        //for(Object o : result1){
        //    System.out.println(o);
        //}

        List<Object> result2 = union(s1, s2); // 并集
        System.out.println("并集");
        for(Object o : result2){
            System.out.println(o);
        }
        test();
    }

    public static void test(){
        List list1 =new ArrayList();
        list1.add("1111");
        list1.add("2222");
        list1.add("3333");
        list1.add("4444");
        list1.add("6666");

        List list2 =new ArrayList();
        list2.add("3333");
        list2.add("4444");
        list2.add("5555");
        list2.add("6666");

        //并集
        //list1.addAll(list2);
        //交集
        //list1.retainAll(list2);
        //差集
        //list1.removeAll(list2);
        //无重复并集
        list1.removeAll(list2);
        //list1.addAll(list2);

        Iterator<String> it=list1.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());

        }

        //System.out.println("-----------------------------------\n");
        //printStr(list1);

    }

    public static void printStr(List list1){
        for (int i = 0; i < list1.size(); i++) {
            System.out.println(list1.get(i));
        }
    }
}

