package com.zhejiang.haoxiadan.model.requestData.out.Chose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/12.
 */

public class DiscussContentDataBean {
    private int evaluateBuyerVal ;
    private ArrayList<String> evaluatePhotos ;
    private int descriptionEvaluate ;
    private String userHeadImg ;
    private long addTtime ;
    private String evaluateInfo ;
    private String userName ;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getEvaluateBuyerVal() {
        return evaluateBuyerVal;
    }

    public void setEvaluateBuyerVal(int evaluateBuyerVal) {
        this.evaluateBuyerVal = evaluateBuyerVal;
    }

    public ArrayList<String> getEvaluatePhotos() {
        return evaluatePhotos;
    }

    public void setEvaluatePhotos(ArrayList<String> evaluatePhotos) {
        this.evaluatePhotos = evaluatePhotos;
    }

    public int getDescriptionEvaluate() {
        return descriptionEvaluate;
    }

    public void setDescriptionEvaluate(int descriptionEvaluate) {
        this.descriptionEvaluate = descriptionEvaluate;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }

    public long getAddTtime() {
        return addTtime;
    }

    public void setAddTtime(long addTtime) {
        this.addTtime = addTtime;
    }

    public String getEvaluateInfo() {
        return evaluateInfo;
    }

    public void setEvaluateInfo(String evaluateInfo) {
        this.evaluateInfo = evaluateInfo;
    }
}
