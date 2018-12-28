package com.essnn.shouyinji.entity;

import java.io.File;
import java.math.BigDecimal;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class ShopData extends BmobObject {
    private String name;
    private BigDecimal money;
    private BmobFile pic;
    private int class_no;
    private Boolean state;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }


    public BmobFile getPic() {
        return pic;
    }

    public void setPic(BmobFile pic) {
        this.pic = pic;
    }


    public int getClass_no() {
        return class_no;
    }

    public void setClass_no(int class_no) {
        this.class_no = class_no;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
