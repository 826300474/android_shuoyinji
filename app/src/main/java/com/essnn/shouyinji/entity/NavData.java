package com.essnn.shouyinji.entity;

import cn.bmob.v3.BmobObject;

public class NavData extends BmobObject {
    private String name;
    private int num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
