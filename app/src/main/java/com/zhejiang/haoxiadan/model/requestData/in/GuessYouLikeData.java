package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by KK on 2017/9/16.
 */
public class GuessYouLikeData {
    private List<GoodsLikeListData> goodsLikeList;

    public List<GoodsLikeListData> getGoodsLikeList() {
        return goodsLikeList;
    }

    public void setGoodsLikeList(List<GoodsLikeListData> goodsLikeList) {
        this.goodsLikeList = goodsLikeList;
    }
}
