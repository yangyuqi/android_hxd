package com.zhejiang.haoxiadan.model.common;

/**
 * Created by wqd on 2017/9/7 0007.
 */

public class OrderMessage {
    private String id;
    private String time;
    private String goodsName;
    private String StatusMessage;
    private String orderFormId;
    private String goodsMainPhoto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getStatusMessage() {
        return StatusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        StatusMessage = statusMessage;
    }

    public String getGoodsMainPhoto() {
        return goodsMainPhoto;
    }

    public void setGoodsMainPhoto(String goodsMainPhoto) {
        this.goodsMainPhoto = goodsMainPhoto;
    }

    public String getOrderFormId() {
        return orderFormId;
    }

    public void setOrderFormId(String orderFormId) {
        this.orderFormId = orderFormId;
    }
}
