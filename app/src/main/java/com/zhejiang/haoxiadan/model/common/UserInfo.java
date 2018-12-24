package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;

/**
 * Created by qiuweiyu on 2017/8/31.
 */

public class UserInfo implements Serializable {
    private String nickName ;
    private String sex ;
    private int  mainIndustryId ;
    private String mainIndustry ;
    private String mainProducts ;
    private String areaId ;
    private String area ;
    private String shengId ;
    private String shiId ;
    private String quId ;
    private String detailAddress ;
    private String userHeadImg ;

    public String getNickname() {
        return nickName;
    }

    public void setNickname(String nickname) {
        this.nickName = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getMainIndustryId() {
        return mainIndustryId;
    }

    public void setMainIndustryId(int mainIndustryId) {
        this.mainIndustryId = mainIndustryId;
    }

    public String getMainIndustry() {
        return mainIndustry;
    }

    public void setMainIndustry(String mainIndustry) {
        this.mainIndustry = mainIndustry;
    }

    public String getMainProducts() {
        return mainProducts;
    }

    public void setMainProducts(String mainProducts) {
        this.mainProducts = mainProducts;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getShengId() {
        return shengId;
    }

    public void setShengId(String shengId) {
        this.shengId = shengId;
    }

    public String getShiId() {
        return shiId;
    }

    public void setShiId(String shiId) {
        this.shiId = shiId;
    }

    public String getQuId() {
        return quId;
    }

    public void setQuId(String quId) {
        this.quId = quId;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }
}
