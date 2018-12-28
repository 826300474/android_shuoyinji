package com.essnn.shouyinji.entity;

import cn.bmob.v3.BmobObject;

public class Order extends BmobObject {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
