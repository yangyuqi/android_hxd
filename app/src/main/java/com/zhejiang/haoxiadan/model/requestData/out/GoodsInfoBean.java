package com.zhejiang.haoxiadan.model.requestData.out;

import java.io.Serializable;

/**
 * Created by qiuweiyu on 2017/8/31.
 */

public class GoodsInfoBean implements Serializable{
    private int id ;
    private String goodsName ;
    private int goodsId ;
    private String goodsMainPhotoPath ;
    private String path ;
    private String storePrice ;
    private int goodsSalenum ;
    private String gcId;

    private String goodsSerial ;
    private String goodsNumType ;

    public String getGoodsNumType() {
        return goodsNumType;
    }

    public void setGoodsNumType(String goodsNumType) {
        this.goodsNumType = goodsNumType;
    }

    public String getGoodsSerial() {
        return goodsSerial;
    }

    public void setGoodsSerial(String goodsSerial) {
        this.goodsSerial = goodsSerial;
    }

    public String getGcId() {
        return gcId;
    }

    public void setGcId(String gcId) {
        this.gcId = gcId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
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
