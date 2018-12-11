package com.essnn.shouyinji.entity;


import com.chad.library.adapter.base.entity.SectionEntity;

import java.math.BigDecimal;

public class ZhangData extends SectionEntity {
    private String name;
    private BigDecimal mon;
    private String time;

    public ZhangData(boolean isHeader, String header) {
        super(isHeader, header);
    }



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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
