package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;

/**
 * Created by qiuweiyu on 2017/8/31.
 */

public class GoodsInfoBean implements Serializable{
    private String goodsName ;
    private String goodsId ;
    private String goodsMainPhotoPath ;
    private String path ;
    private String storePrice ;
    private int goodsSalenum ;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsMainPhotoPath() {
        return goodsMainPhotoPath;
    }

    public void setGoodsMainPhotoPath(String goodsMainPhotoPath) {
        this.goodsMainPhotoPath = goodsMainPhotoPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStorePrice() {
        return storePrice;
    }

    public void setStorePrice(String storePrice) {
        this.storePrice = storePrice;
    }

    public int getGoodsSalenum() {
        return goodsSalenum;
    }

    public void setGoodsSalenum(int goodsSalenum) {
        this.goodsSalenum = goodsSalenum;
    }
}
