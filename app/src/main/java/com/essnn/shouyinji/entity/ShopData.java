package com.essnn.shouyinji.entity;

import java.math.BigDecimal;

public class ShopData {
    private String name;
    private BigDecimal mon;

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public BigDecimal getMon() {
        return mon;
    }

    public void setMon(BigDecimal mon) {
        this.mon = mon;
    }
}
