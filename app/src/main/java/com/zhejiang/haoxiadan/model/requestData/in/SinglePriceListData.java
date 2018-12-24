package com.zhejiang.haoxiadan.model.requestData.in;

/**
 * Created by KK on 2017/9/5.
 */
public class SinglePriceListData {
    private String singleGoodsPrice;//
    private String goodsId;//

    public String getSingleGoodsPrice() {
        return singleGoodsPrice;
    }

    public void setSingleGoodsPrice(String singleGoodsPrice) {
        this.singleGoodsPrice = singleGoodsPrice;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
