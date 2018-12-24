package com.zhejiang.haoxiadan.model.choseModel;

/**
 * Created by qiuweiyu on 2017/11/25.
 */

public class SendIfBean {
    private String type ;
    private String goodsList ;
    private String lower_price ;
    private String upper_price ;

    public SendIfBean(String type, String goodsList, String lower_price, String upper_price) {
        this.type = type;
        this.goodsList = goodsList;
        this.lower_price = lower_price;
        this.upper_price = upper_price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(String goodsList) {
        this.goodsList = goodsList;
    }

    public String getLower_price() {
        return lower_price;
    }

    public void setLower_price(String lower_price) {
        this.lower_price = lower_price;
    }

    public String getUpper_price() {
        return upper_price;
    }

    public void setUpper_price(String upper_price) {
        this.upper_price = upper_price;
    }
}
