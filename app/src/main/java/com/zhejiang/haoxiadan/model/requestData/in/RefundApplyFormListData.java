package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by KK on 2017/9/7.
 */
public class RefundApplyFormListData {
    private long addTime;//
    private String orderId;//
    private String userPhone;//
    private String userApplyExplain;//
    private String userName;//
    private String storeId;//
    private int failRefund;//
    private long userId;//
//    private String userApplyImg;//
    private String userHeadImg;//
    private String userApplyReason;//
    private long orderFormId;//
    private String storeLogo;//
    private String storeName;//
    private String refundTradeNo;//
    private double refundPrice;//
    private int status;//
    private String auditFailReason;//	审核失败原因		String
    private long auditDate;//	审核时间		Int
    private String auditUserName;//	审核人姓名		String

    private List<String> userApplyImg;//

    public String getAuditFailReason() {
        return auditFailReason;
    }

    public void setAuditFailReason(String auditFailReason) {
        this.auditFailReason = auditFailReason;
    }

    public long getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(long auditDate) {
        this.auditDate = auditDate;
    }

    public String getAuditUserName() {
        return auditUserName;
    }

    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserApplyExplain() {
        return userApplyExplain;
    }

    public void setUserApplyExplain(String userApplyExplain) {
        this.userApplyExplain = userApplyExplain;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public int getFailRefund() {
        return failRefund;
    }

    public void setFailRefund(int failRefund) {
        this.failRefund = failRefund;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<String> getUserApplyImg() {
        return userApplyImg;
    }

    public void setUserApplyImg(List<String> userApplyImg) {
        this.userApplyImg = userApplyImg;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }

    public String getUserApplyReason() {
        return userApplyReason;
    }

    public void setUserApplyReason(String userApplyReason) {
        this.userApplyReason = userApplyReason;
    }

    public long getOrderFormId() {
        return orderFormId;
    }

    public void setOrderFormId(long orderFormId) {
        this.orderFormId = orderFormId;
    }

    public String getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getRefundTradeNo() {
        return refundTradeNo;
    }

    public void setRefundTradeNo(String refundTradeNo) {
        this.refundTradeNo = refundTradeNo;
    }

    public double getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(double refundPrice) {
        this.refundPrice = refundPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
