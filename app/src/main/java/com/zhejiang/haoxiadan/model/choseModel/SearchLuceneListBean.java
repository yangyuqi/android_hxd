package com.zhejiang.haoxiadan.model.choseModel;

/**
 * Created by qiuweiyu on 2017/9/7.
 */

public class SearchLuceneListBean {
    private int vo_id ;
    private String vo_title ;
    private String vo_type ;
    private String vo_storeId ;
    private String vo_store_price ;
    private String storeLogoImg ;
    private String vo_goods_collect ;
    private int monthlySales ;
    private String storeArea ;
    private String storeName ;
    private String mainIndustry ;
    private String mainProducts ;
    private String yearSales ;
    private String salesVolume ;
    private String goodsLuceneList ;
    private String vo_main_photo_url ;

    private String gcIdThird ;

    private int vo_goods_salenum ;
    private String vo_goods_serial ;

    public int getVo_goods_salenum() {
        return vo_goods_salenum;
    }

    public void setVo_goods_salenum(int vo_goods_salenum) {
        this.vo_goods_salenum = vo_goods_salenum;
    }

    public String getVo_goods_serial() {
        return vo_goods_serial;
    }

    public void setVo_goods_serial(String vo_goods_serial) {
        this.vo_goods_serial = vo_goods_serial;
    }

    public String getGcIdThird() {
        return gcIdThird;
    }

    public void setGcIdThird(String gcIdThird) {
        this.gcIdThird = gcIdThird;
    }

    public String getGoods_numType() {
        return goods_numType;
    }

    public void setGoods_numType(String goods_numType) {
        this.goods_numType = goods_numType;
    }

    private String goods_numType ;


    public String getVo_main_photo_url() {
        return vo_main_photo_url;
    }

    public void setVo_main_photo_url(String vo_main_photo_url) {
        this.vo_main_photo_url = vo_main_photo_url;
    }

    public int getVo_id() {
        return vo_id;
    }

    public void setVo_id(int vo_id) {
        this.vo_id = vo_id;
    }

    public String getStoreLogoImg() {
        return storeLogoImg;
    }

    public void setStoreLogoImg(String storeLogoImg) {
        this.storeLogoImg = storeLogoImg;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getVo_title() {
        return vo_title;
    }

    public void setVo_title(String vo_title) {
        this.vo_title = vo_title;
    }

    public String getVo_type() {
        return vo_type;
    }

    public void setVo_type(String vo_type) {
        this.vo_type = vo_type;
    }

    public String getVo_storeId() {
        return vo_storeId;
    }

    public void setVo_storeId(String vo_storeId) {
        this.vo_storeId = vo_storeId;
    }



    public String getVo_store_price() {
        return vo_store_price;
    }

    public void setVo_store_price(String vo_store_price) {
        this.vo_store_price = vo_store_price;
    }


    public String getVo_goods_collect() {
        return vo_goods_collect;
    }

    public void setVo_goods_collect(String vo_goods_collect) {
        this.vo_goods_collect = vo_goods_collect;
    }

    public int getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(int monthlySales) {
        this.monthlySales = monthlySales;
    }

    public String getStoreArea() {
        return storeArea;
    }

    public void setStoreArea(String storeArea) {
        this.storeArea = storeArea;
    }

    public String getMainIndustry() {
        return mainIndustry;
    }

    public void setMainIndustry(String mainIndustry) {
        this.mainIndustry = mainIndustry;
    }

    public String getMainProducts() {
        return mainProducts;
    }

    public void setMainProducts(String mainProducts) {
        this.mainProducts = mainProducts;
    }

    public String getYearSales() {
        return yearSales;
    }

    public void setYearSales(String yearSales) {
        this.yearSales = yearSales;
    }

    public String getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(String salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getGoodsLuceneList() {
        return goodsLuceneList;
    }

    public void setGoodsLuceneList(String goodsLuceneList) {
        this.goodsLuceneList = goodsLuceneList;
    }
}
