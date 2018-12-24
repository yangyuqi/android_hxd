package com.zhejiang.haoxiadan.model.requestData.out;

import java.io.Serializable;

public class DeliveryMessageBean implements Serializable{
    private String note ;
    private String orderNo ;
    private String business ;
    private Integer deliveryType ;
    private Integer goodsCartId ;
    private Integer deliveryCount ;
    private Integer  status ;
    private String confirmDate ;

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Integer getGoodsCartId() {
        return goodsCartId;
    }

    public void setGoodsCartId(Integer goodsCartId) {
        this.goodsCartId = goodsCartId;
    }

    public Integer getDeliveryCount() {
        return deliveryCount;
    }

    public void setDeliveryCount(Integer deliveryCount) {
        this.deliveryCount = deliveryCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
