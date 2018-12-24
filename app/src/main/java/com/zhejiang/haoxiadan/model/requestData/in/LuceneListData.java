package com.zhejiang.haoxiadan.model.requestData.in;

/**
 * Created by KK on 2017/9/13.
 */
public class LuceneListData {
    private long vo_id;//		是	String
    private String vo_title;//		是	String
    private String vo_type;//	搜索属性	否	String
    private String vo_storeId;//	商家ID	否	String
    private int vo_goods_salenum;//	商品销量	否	String
    private double vo_store_price;//	商品价格	否	String
    private String vo_main_photo_url;//	商品图片路径	否	String
    private int vo_goods_collect;//	商品收藏数	否	String
    private int monthlySales;//	月销量	否	String
    private String storeArea;//		否	String
    private String mainIndustry;//		否	String
    private String mainProducts;//		否	String
    private String yearSales;//		否	String
    private int salesVolume;//		否	String
    private String goodsLuceneList;//		否	String
    private String goods_numType ;

    public String getGoods_numType() {
        return goods_numType;
    }

    public long getVo_id() {
        return vo_id;
    }

    public void setVo_id(long vo_id) {
        this.vo_id = vo_id;
    }

    public void setGoods_numType(String goods_numType) {
        this.goods_numType = goods_numType;
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

    public int getVo_goods_salenum() {
        return vo_goods_salenum;
    }

    public void setVo_goods_salenum(int vo_goods_salenum) {
        this.vo_goods_salenum = vo_goods_salenum;
    }

    public double getVo_store_price() {
        return vo_store_price;
    }

    public void setVo_store_price(double vo_store_price) {
        this.vo_store_price = vo_store_price;
    }

    public String getVo_main_photo_url() {
        return vo_main_photo_url;
    }

    public void setVo_main_photo_url(String vo_main_photo_url) {
        this.vo_main_photo_url = vo_main_photo_url;
    }

    public int getVo_goods_collect() {
        return vo_goods_collect;
    }

    public void setVo_goods_collect(int vo_goods_collect) {
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

    public int getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(int salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getGoodsLuceneList() {
        return goodsLuceneList;
    }

    public void setGoodsLuceneList(String goodsLuceneList) {
        this.goodsLuceneList = goodsLuceneList;
    }
}
