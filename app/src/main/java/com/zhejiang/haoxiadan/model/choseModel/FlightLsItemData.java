package com.zhejiang.haoxiadan.model.choseModel;

import com.zhejiang.haoxiadan.model.requestData.out.SpecPropertyBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/6.
 */

public class FlightLsItemData implements Serializable{
    private int specpropertyId ;
    private String specpropertyCrowdSum ;
    private String specpropertyImg ;
    private String specpropertyName ;
    private List<FlightLsItemBean> specpropertyList ;
    private String color ;




    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSpecpropertyId() {
        return specpropertyId;
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

    public String getSpecpropertyImg() {
        return specpropertyImg;
    }

    public void setSpecpropertyImg(String specpropertyImg) {
        this.specpropertyImg = specpropertyImg;
    }

    public String getSpecpropertyName() {
        return specpropertyName;
    }

    public void setSpecpropertyName(String specpropertyName) {
        this.specpropertyName = specpropertyName;
    }

    public List<FlightLsItemBean> getSpecpropertyList() {
        return specpropertyList;
    }

    public void setSpecpropertyList(List<FlightLsItemBean> specpropertyList) {
        this.specpropertyList = specpropertyList;
    }
}
