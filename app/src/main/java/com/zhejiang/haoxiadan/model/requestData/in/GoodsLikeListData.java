package com.zhejiang.haoxiadan.model.requestData.in;

/**
 * Created by KK on 2017/9/16.
 */
public class GoodsLikeListData {
    private String goods_name;//
    private long id;//
    private long goods_type;//
    private String goods_photos_type;//
    private String Path;//
    private long gc_id;//
    private long collCount;//
    private double goods_current_price;//
    private int monthlySales;//
    private String goodsSerial;//货号

    public String getGoodsSerial() {
        return goodsSerial;
    }

    public void setGoodsSerial(String goodsSerial) {
        this.goodsSerial = goodsSerial;
    }

    public int getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(int monthlySales) {
        this.monthlySales = monthlySales;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(long goods_type) {
        this.goods_type = goods_type;
    }

    public String getGoods_photos_type() {
        return goods_photos_type;
    }

    public void setGoods_photos_type(String goods_photos_type) {
        this.goods_photos_type = goods_photos_type;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public long getGc_id() {
        return gc_id;
    }

    public void setGc_id(long gc_id) {
        this.gc_id = gc_id;
    }

    public long getCollCount() {
        return collCount;
    }

    public void setCollCount(long collCount) {
        this.collCount = collCount;
    }

    public double getGoods_current_price() {
        return goods_current_price;
    }

    public void setGoods_current_price(double goods_current_price) {
        this.goods_current_price = goods_current_price;
    }
}
