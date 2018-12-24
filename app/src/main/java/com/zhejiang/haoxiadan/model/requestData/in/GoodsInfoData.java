package com.zhejiang.haoxiadan.model.requestData.in;

/**
 * Created by KK on 2017/9/5.
 */
public class GoodsInfoData {
    private double goods_price;//	商品原价	是	BigDecimal
    private String goods_gsp_val;//	规格详情	是	String
    private double goods_all_price;//	商品总价	是	BigDecimal
    private String goods_id;//	商品id	是	String
    private String goods_name;//	商品名称	是	String
    private String goods_domainPath;//		是	String
    private String goods_serial;//	商品货号	否	String
    private String goods_mainphoto_path;//	商品主图片	是	String
    private String goods_gsp_ids;//	商品规格	是	String
    private int goods_count;//	商品数量	是	Int

    public double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_gsp_val() {
        return goods_gsp_val;
    }

    public void setGoods_gsp_val(String goods_gsp_val) {
        this.goods_gsp_val = goods_gsp_val;
    }

    public double getGoods_all_price() {
        return goods_all_price;
    }

    public void setGoods_all_price(double goods_all_price) {
        this.goods_all_price = goods_all_price;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_domainPath() {
        return goods_domainPath;
    }

    public void setGoods_domainPath(String goods_domainPath) {
        this.goods_domainPath = goods_domainPath;
    }

    public String getGoods_serial() {
        return goods_serial;
    }

    public void setGoods_serial(String goods_serial) {
        this.goods_serial = goods_serial;
    }

    public String getGoods_mainphoto_path() {
        return goods_mainphoto_path;
    }

    public void setGoods_mainphoto_path(String goods_mainphoto_path) {
        this.goods_mainphoto_path = goods_mainphoto_path;
    }

    public String getGoods_gsp_ids() {
        return goods_gsp_ids;
    }

    public void setGoods_gsp_ids(String goods_gsp_ids) {
        this.goods_gsp_ids = goods_gsp_ids;
    }

    public int getGoods_count() {
        return goods_count;
    }

    public void setGoods_count(int goods_count) {
        this.goods_count = goods_count;
    }
}
