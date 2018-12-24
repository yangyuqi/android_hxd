package com.zhejiang.haoxiadan.model.choseModel;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/5.
 */

public class GoodsListData {
    private List<GoodsIdListBeanData> goodsList ;

    public GoodsListData(List<GoodsIdListBeanData> goodsList) {
        this.goodsList = goodsList;
    }

    public List<GoodsIdListBeanData> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsIdListBeanData> goodsList) {
        this.goodsList = goodsList;
    }
}
