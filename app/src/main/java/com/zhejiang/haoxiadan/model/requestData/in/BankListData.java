package com.zhejiang.haoxiadan.model.requestData.in;

/**
 * Created by KK on 2017/9/8.
 */
public class BankListData {
    private long id;//
    private String bankId;//
    private String bankName;//
    private String bankNumHide;
    private String userPhone;//

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getBankNumHide() {
        return bankNumHide;
    }

    public void setBankNumHide(String bankNumHide) {
        this.bankNumHide = bankNumHide;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
