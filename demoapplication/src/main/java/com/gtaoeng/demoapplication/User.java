package com.gtaoeng.demoapplication;

import com.gtaoeng.viewbuilder.MXField;

public class User {

    private int id;
    @MXField(isMust = true)
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
