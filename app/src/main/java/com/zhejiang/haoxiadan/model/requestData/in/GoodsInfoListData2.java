package com.zhejiang.haoxiadan.model.requestData.in;

/**
 * Created by KK on 2017/9/6.
 */
public class GoodsInfoListData2 {
    private String goods_name;//
    private int goodsSalenum;//
    private long goodsId;//
    private String goods_details;//
    private String goodsMainPhoto;//
    private String goodsMainPhotoPath;//
    private String ext;//
    private double store_price;//
    private double storePrice;
    private int goods_collect;
    private int monthlySales;
    private int goodsNumType ;

    public int getGoodsNumType() {
        return goodsNumType;
    }

    public void setGoodsNumType(int goodsNumType) {
        this.goodsNumType = goodsNumType;
    }

    public int getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(int monthlySales) {
        this.monthlySales = monthlySales;
    }

    public double getStorePrice() {
        return storePrice;
    }

    public void setStorePrice(double storePrice) {
        this.storePrice = storePrice;
    }

    public String getGoodsMainPhotoPath() {
        return goodsMainPhotoPath;
    }

    public void setGoodsMainPhotoPath(String goodsMainPhotoPath) {
        this.goodsMainPhotoPath = goodsMainPhotoPath;
    }

    public double getStore_price() {
        return store_price;
    }

    public void setStore_price(double store_price) {
        this.store_price = store_price;
    }

    public int getGoods_collect() {
        return goods_collect;
    }

    public void setGoods_collect(int goods_collect) {
        this.goods_collect = goods_collect;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getGoodsSalenum() {
        return goodsSalenum;
    }

    public void setGoodsSalenum(int goodsSalenum) {
        this.goodsSalenum = goodsSalenum;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoods_details() {
        return goods_details;
    }

    public void setGoods_details(String goods_details) {
        this.goods_details = goods_details;
    }

    public String getGoodsMainPhoto() {
        return goodsMainPhoto;
    }

    public void setGoodsMainPhoto(String goodsMainPhoto) {
        this.goodsMainPhoto = goodsMainPhoto;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
