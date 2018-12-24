package com.zhejiang.haoxiadan.model.requestData.in;

import com.zhejiang.haoxiadan.model.requestData.out.DeliveryMessageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KK on 2017/9/6.
 */
public class GoodsGspData2 {
    private double goodsPrice;//
    private int count;//
    private List<String> goodsSpecContent;//
    private List<String> goodsSpecName;
    private double goodsSinglePrice;//
    private String cartGsp;//
    private Integer refundStatus ;
    private Integer refundType ;
    private int goodsCartId ;

    private Object delivery ;

    public Object getDelivery() {
        return delivery;
    }

    public void setDelivery(Object delivery) {
        this.delivery = delivery;
    }

    public int getGoodsCartId() {
        return goodsCartId;
    }

    public void setGoodsCartId(int goodsCartId) {
        this.goodsCartId = goodsCartId;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Integer getRefundType() {
        return refundType;
    }

    public void setRefundType(Integer refundType) {
        this.refundType = refundType;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getGoodsSpecContent() {
        return goodsSpecContent;
    }

    public void setGoodsSpecContent(List<String> goodsSpecContent) {
        this.goodsSpecContent = goodsSpecContent;
    }

    public List<String> getGoodsSpecName() {
        return goodsSpecName;
    }

    public void setGoodsSpecName(List<String> goodsSpecName) {
        this.goodsSpecName = goodsSpecName;
    }

    public double getGoodsSinglePrice() {
        return goodsSinglePrice;
    }

    public void setGoodsSinglePrice(double goodsSinglePrice) {
        this.goodsSinglePrice = goodsSinglePrice;
    }

    public String getCartGsp() {
        return cartGsp;
    }

    public void setCartGsp(String cartGsp) {
        this.cartGsp = cartGsp;
    }
}
