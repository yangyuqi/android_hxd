package com.zhejiang.haoxiadan.model.requestData.in;

/**
 * Created by qiuweiyu on 2017/9/4.
 */

public class FavoritrBean {
    private String favouriteSource ;
    private String goodsId ;

    public FavoritrBean(String favouriteSource, String goodsId) {
        this.favouriteSource = favouriteSource;
        this.goodsId = goodsId;
    }

    public String getFavouriteSource() {
        return favouriteSource;
    }

    public void setFavouriteSource(String favouriteSource) {
        this.favouriteSource = favouriteSource;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
