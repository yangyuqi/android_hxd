package com.zhejiang.haoxiadan.model.choseModel;

/**
 * Created by qiuweiyu on 2017/9/7.
 */

public class SearchGoodsPropertyBean {
    private int goodsTypePropertyId ;
    private String name ;
    private String value ;

    public int getGoodsTypePropertyId() {
        return goodsTypePropertyId;
    }

    public void setGoodsTypePropertyId(int goodsTypePropertyId) {
        this.goodsTypePropertyId = goodsTypePropertyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
