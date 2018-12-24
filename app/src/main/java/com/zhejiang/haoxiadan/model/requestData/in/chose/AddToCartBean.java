package com.zhejiang.haoxiadan.model.requestData.in.chose;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/4.
 */

public class AddToCartBean {
    private String goodsId ;
    private String cartGsp ;
    private int goodsNumType ;
    private List<String> goodsSpecName ;
    private List<String> goodsSpecContent ;
    private int count ;

    public int getGoodsNumType() {
        return goodsNumType;
    }

    public void setGoodsNumType(int goodsNumType) {
        this.goodsNumType = goodsNumType;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getCartGsp() {
        return cartGsp;
    }

    public void setCartGsp(String cartGsp) {
        this.cartGsp = cartGsp;
    }

    public List<String> getGoodsSpecName() {
        return goodsSpecName;
    }

    public void setGoodsSpecName(List<String> goodsSpecName) {
        this.goodsSpecName = goodsSpecName;
    }

    public List<String> getGoodsSpecContent() {
        return goodsSpecContent;
    }

    public void setGoodsSpecContent(List<String> goodsSpecContent) {
        this.goodsSpecContent = goodsSpecContent;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
