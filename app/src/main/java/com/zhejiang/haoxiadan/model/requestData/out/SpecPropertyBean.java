package com.zhejiang.haoxiadan.model.requestData.out;

import java.io.Serializable;

/**
 * Created by qiuweiyu on 2017/9/1.
 */

public class SpecPropertyBean implements Serializable{
    private int specpropertyId ;
    private String specpropertyName ;
    private String specpropertyInventCount ;
    private String specpropertySmallCount ;
    private String specpropertySerial ;
    private int checkOutCount ;

    public int getCheckOutCount() {
        return checkOutCount;
    }

    public void setCheckOutCount(int checkOutCount) {
        this.checkOutCount = checkOutCount;
    }

    public String getSpecpropertySmallCount() {
        return specpropertySmallCount;
    }

    public void setSpecpropertySmallCount(String specpropertySmallCount) {
        this.specpropertySmallCount = specpropertySmallCount;
    }

    public String getSpecpropertySerial() {
        return specpropertySerial;
    }

    public void setSpecpropertySerial(String specpropertySerial) {
        this.specpropertySerial = specpropertySerial;
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
}
