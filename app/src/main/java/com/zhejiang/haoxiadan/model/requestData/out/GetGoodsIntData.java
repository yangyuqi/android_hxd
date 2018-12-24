package com.zhejiang.haoxiadan.model.requestData.out;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/1.
 */

public class GetGoodsIntData implements Serializable{
    private int specpropertyId ;
    private String specpropertyCrowdSum ;
    private String specpropertyName ;
    private String oldFileNameGsp ;
    private String specpropertyImg ;
    private List<SpecPropertyBean> specpropertyList ;


    public String getSpecpropertyImg() {
        return specpropertyImg;
    }

    public void setSpecpropertyImg(String specpropertyImg) {
        this.specpropertyImg = specpropertyImg;
    }

    public int getSpecpropertyId() {
        return specpropertyId;
    }

    public String getOldFileNameGsp() {
        return oldFileNameGsp;
    }

    public void setOldFileNameGsp(String oldFileNameGsp) {
        this.oldFileNameGsp = oldFileNameGsp;
    }

    public void setSpecpropertyId(int specpropertyId) {
        this.specpropertyId = specpropertyId;
    }

    public String getSpecpropertyCrowdSum() {
        return specpropertyCrowdSum;
    }

    public void setSpecpropertyCrowdSum(String specpropertyCrowdSum) {
        this.specpropertyCrowdSum = specpropertyCrowdSum;
    }

    public String getSpecpropertyName() {
        return specpropertyName;
    }

    public void setSpecpropertyName(String specpropertyName) {
        this.specpropertyName = specpropertyName;
    }

    public List<SpecPropertyBean> getSpecpropertyList() {
        return specpropertyList;
    }

    public void setSpecpropertyList(List<SpecPropertyBean> specpropertyList) {
        this.specpropertyList = specpropertyList;
    }
}
