package com.dong4j.entity;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author
 * @version 1.0
 * @date
 */
public class ExcelEntity {
    private String sheetName; // excel 名称

    private String[] columnNames; // 列名

    private String[] propertyNames; // 属性名称

    private String[] cLabels;

    private int rpp = 200;

    private HSSFCellStyle style = null;

    private List<Object> resultList = new ArrayList<>();

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public String[] getPropertyNames() {
        return propertyNames;
    }

    public void setPropertyNames(String[] propertyNames) {
        this.propertyNames = propertyNames;
    }

    public String[] getCLabels() {
        return cLabels;
    }

    public void setCLabels(String[] labels) {
        cLabels = labels;
    }

    public int getRpp() {
        return rpp;
    }

    public void setRpp(int rpp) {
        this.rpp = rpp;
    }

    public HSSFCellStyle getStyle() {
        return style;
    }

    public void setStyle(HSSFCellStyle style) {
        this.style = style;
    }

    public List<Object> getResultList() {
        return resultList;
    }

    public void setResultList(List<Object> resultList) {
        this.resultList = resultList;
    }
}
