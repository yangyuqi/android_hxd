package com.zhejiang.haoxiadan.model.requestData.out.my;

import java.io.Serializable;

/**
 * Created by qiuweiyu on 2017/9/5.
 */

public class CurrentUserAddressBean implements Serializable{
    private String trueName ;
    private int id ;
    private String provinceId ;
    private String provinceName ;
    private String cityId ;
    private String cityName ;
    private String areaId ;
    private String areaName ;
    private String area_info ;
    private String telephone  ;
    private String mobile ;
    private int default_val ;

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getArea_info() {
        return area_info;
    }

    public void setArea_info(String area_info) {
        this.area_info = area_info;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getDefault_val() {
        return default_val;
    }

    public void setDefault_val(int default_val) {
        this.default_val = default_val;
    }
}
