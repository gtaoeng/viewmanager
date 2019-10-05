package com.gtaoeng.demoapplication;

import com.gtaoeng.viewbuilder.MXField;

public class ClassModel {

    @MXField(nameEn = "名称1", fieldType = MXField.FeldType.NormalType, sort = 1,isMust = true)
    private String name;
    @MXField(nameEn = "名称2", fieldType = MXField.FeldType.SelectType, sort = 1,isMust = true)
    private String name2;
    @MXField(nameEn = "名称3", fieldType = MXField.FeldType.MultipleSelectType, sort = 1)
    private String name3;
    @MXField(nameEn = "电话", fieldType = MXField.FeldType.PhoneType, sort = 1)
    private String phone;
    @MXField(nameEn = "自定义选择", fieldType = MXField.FeldType.StaticSelectType, sort = 1)
    private String select;

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
