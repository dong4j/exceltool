package com.dong4j.entity;

/**
 * Created by: dong4j.
 * Date: 2016-12-30.
 * Time: 23:57.
 * Description: 解析数据实体对象
 */
public class AnalysisEntity {
    private int sheetIndex;
    private int rowNum;
    private int column;
    private Object data;

    public int getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnalysisEntity that = (AnalysisEntity) o;
        return data.equals(that.data);
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }
}
