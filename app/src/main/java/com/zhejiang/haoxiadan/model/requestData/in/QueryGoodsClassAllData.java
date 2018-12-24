package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by KK on 2017/9/5.
 */
public class QueryGoodsClassAllData {
    private List<GoodsClassData> goodsClass;

    public List<GoodsClassData> getGoodsClass() {
        return goodsClass;
    }

    public void setGoodsClass(List<GoodsClassData> goodsClass) {
        this.goodsClass = goodsClass;
    }
}
