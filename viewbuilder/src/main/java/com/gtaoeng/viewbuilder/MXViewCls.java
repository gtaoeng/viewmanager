package com.gtaoeng.viewbuilder;

import java.util.List;

public class MXViewCls {


    public final static int NormalType = 0;
    public final static int NumberType = 1;
    public final static int FloatType = 2;
    public final static int NoteType = 3;
    public final static int PhoneType = 4;
    public final static int SelectType = 5;
    public final static int MultipleSelectType = 51;
    public final static int DateType = 6;
    public final static int MapType = 7;
    public final static int PswType =8;

    /**
     * 显示名称
     */
    private String displayName;
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 字段类型 0:字符串，1:数字，2：小数，3:长文本，4：选择，5：日期,
     */
    private int fieldType;
    /**
     * 字段内容
     */
    private String fieldValue;

    /**
     * 是否可编辑
     */
    private boolean canEdit;

    /**
     * 排版顺序
     */
    private int sort;

    /**
     * 当fieldType=4时选择数据结构
     */
    private List<?> selectDatas;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getFieldType() {
        return fieldType;
    }

    public void setFieldType(int fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<?> getSelectDatas() {
        return selectDatas;
    }

    public void setSelectDatas(List<?> selectDatas) {
        this.selectDatas = selectDatas;
    }
}
