package com.zhejiang.haoxiadan.model.choseModel;

import java.io.Serializable;

/**
 * Created by qiuweiyu on 2017/9/6.
 */

public class FlightLsItemBean implements Serializable{
    private int specpropertyId ;
    private String specpropertyName ;
    private String specpropertyInventCount ;
    private String specpropertySmallCount ;
    private String price ;
    private int count ;
    private int checkOutCount ;

    private String color ;
    private int outerId ;

    public int getCheckOutCount() {
        return checkOutCount;
    }

    public void setCheckOutCount(int checkOutCount) {
        this.checkOutCount = checkOutCount;
    }

    public int getOuterId() {
        return outerId;
    }

    public void setOuterId(int outerId) {
        this.outerId = outerId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSpecpropertySmallCount() {
        return specpropertySmallCount;
    }

    public void setSpecpropertySmallCount(String specpropertySmallCount) {
        this.specpropertySmallCount = specpropertySmallCount;
    }

    public int getSpecpropertyId() {
        return specpropertyId;
    }

    public void setSpecpropertyId(int specpropertyId) {
        this.specpropertyId = specpropertyId;
    }

    public String getSpecpropertyName() {
        return specpropertyName;
    }

    public void setSpecpropertyName(String specpropertyName) {
        this.specpropertyName = specpropertyName;
    }

    public String getSpecpropertyInventCount() {
        return specpropertyInventCount;
    }

    public void setSpecpropertyInventCount(String specpropertyInventCount) {
        this.specpropertyInventCount = specpropertyInventCount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
