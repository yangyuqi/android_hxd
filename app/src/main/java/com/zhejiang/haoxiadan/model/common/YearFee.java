package com.zhejiang.haoxiadan.model.common;

/**
 * 年费
 * Created by KK on 2017/8/23.
 */
public class YearFee {
    private String id;
    //名称
    private String name;
    //年
    private int year;
    //单价
    private double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
