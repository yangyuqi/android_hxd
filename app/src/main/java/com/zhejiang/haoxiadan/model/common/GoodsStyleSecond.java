package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;

/**
 * 商品的规格（第二层）
 * Created by KK on 2017/8/23.
 */
public class GoodsStyleSecond implements Serializable {
    private String id;
    //规格名
    private String title;
    //单价
    private double price;
    //库存
    private int stock;
    //购买数量
    private int buyNum;
    //总价
    private double totalPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
