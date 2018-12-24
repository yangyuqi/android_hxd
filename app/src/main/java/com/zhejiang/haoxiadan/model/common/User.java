package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;

/**
 * Created by KK on 2017/2/22.
 */

public class User implements Serializable{

    private String id;
    private String accessToken;
    private String refreshToken;
    //用户类型
    private USER_TYPE userType = USER_TYPE.COMMON;
    //头像
    private String userHeadImg;
    //手机
    private String mobile;
    //昵称
    private String nickName;
    //手机
    private String userName ;

    //用户角色
    private String userRole;

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    //性别
    private String sex;
    //主营行业
    private String industry;
    //主营产品
    private String mainProduct;


    public enum USER_TYPE{
        COMMON,   //普通会员
        BUYER, //采购商
        SELLER,  //供应商
        SERVICE     //客服
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getMainProduct() {
        return mainProduct;
    }

    public void setMainProduct(String mainProduct) {
        this.mainProduct = mainProduct;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHeadIcon() {
        return userHeadImg;
    }

    public void setHeadIcon(String headIcon) {
        this.userHeadImg = headIcon;
    }

    public USER_TYPE getUserType() {
        return userType;
    }

    public void setUserType(USER_TYPE userType) {
        this.userType = userType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
