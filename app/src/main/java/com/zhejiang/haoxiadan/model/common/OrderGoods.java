package com.zhejiang.haoxiadan.model.common;

import com.zhejiang.haoxiadan.model.requestData.out.DeliveryMessageBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KK on 2017/8/23.
 */
public class OrderGoods implements Serializable{
    private String id;
    //图标
    private String icon;
    //商品名称
    private String title;
    //商品价格
    private String price;
    //商品规格
    private String style;
    //商品数量
    private String count;

    private String per_count ;

    private String refundStatus ;

    private String refundType ;

    private String orderStatus ;

    private int goodsCartId ;

    private ArrayList<DeliveryMessageBean> delivery ;

    private String storeName ;
    private String storeId ;
    private String orderId ;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public ArrayList<DeliveryMessageBean> getDelivery() {
        return delivery;
    }

    public void setDelivery(ArrayList<DeliveryMessageBean> delivery) {
        this.delivery = delivery;
    }

    public int getGoodsCartId() {
        return goodsCartId;
    }

    public void setGoodsCartId(int goodsCartId) {
        this.goodsCartId = goodsCartId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getPer_count() {
        return per_count;
    }

    public void setPer_count(String per_count) {
        this.per_count = per_count;
    }

    //商品类型
    private GOODS_TYPE type = GOODS_TYPE.STOCK;

    public enum GOODS_TYPE{
        STOCK,//现货
        FUTURES//期货
    }

    public GOODS_TYPE getType() {
        return type;
    }

    public void setType(GOODS_TYPE type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
