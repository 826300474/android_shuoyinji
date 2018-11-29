package com.essnn.shouyinji.entity;

import java.math.BigDecimal;

public class BuyData {
    private String name;
    private BigDecimal mon;
    private int num;
    private BigDecimal zongjia;

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

    public int getNum() {

        return num;
    }

    public void setNum(int num) {

        this.num = num;
    }

    public BigDecimal getZongjia() {

        return zongjia;
    }
    public void setZongjia(BigDecimal zongjia) {

        this.zongjia = zongjia;
    }
}
