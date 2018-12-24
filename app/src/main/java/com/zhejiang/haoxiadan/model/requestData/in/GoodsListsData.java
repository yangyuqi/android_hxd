package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/1/11.
 */

public class GoodsListsData {
    private String storeId;//
    private String storeName;//
    private double goodsPrice;//
    private double goodsShipPrice;//
    private List<GoodsVoListData> goodsList ;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public double getGoodsShipPrice() {
        return goodsShipPrice;
    }

    public void setGoodsShipPrice(double goodsShipPrice) {
        this.goodsShipPrice = goodsShipPrice;
    }

    public List<GoodsVoListData> getGoodsLists() {
        return goodsList;
    }

    public void setGoodsLists(List<GoodsVoListData> goodsLists) {
        this.goodsList = goodsLists;
    }
}
