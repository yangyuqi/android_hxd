package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;

/**
 * 银行卡
 * Created by KK on 2017/9/8.
 */
public class BankCard implements Serializable {

    private String id;
    private String bankNo;
    private String bankName;
    private String cardNo;
    private CARD_TYPE type;
    private String userName;
    private String identCardNo;
    private String mobile;
    private String date;
    private String cvn;

    public enum CARD_TYPE{
        COMMON,
        CREDIT
    }

    @Override
    public String toString() {
        return bankName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public CARD_TYPE getType() {
        return type;
    }

    public void setType(CARD_TYPE type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdentCardNo() {
        return identCardNo;
    }

    public void setIdentCardNo(String identCardNo) {
        this.identCardNo = identCardNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCvn() {
        return cvn;
    }

    public void setCvn(String cvn) {
        this.cvn = cvn;
    }
}
