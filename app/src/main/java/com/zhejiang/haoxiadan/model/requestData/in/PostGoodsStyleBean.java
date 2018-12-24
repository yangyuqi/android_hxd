package com.zhejiang.haoxiadan.model.requestData.in;

import com.zhejiang.haoxiadan.model.requestData.out.GetGoodsIntData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/1.
 */

public class PostGoodsStyleBean implements Serializable{
    private List<GetGoodsIntData> goodsInvent ;

    public List<GetGoodsIntData> getGoodsInvent() {
        return goodsInvent;
    }

    public void setGoodsInvent(List<GetGoodsIntData> goodsInvent) {
        this.goodsInvent = goodsInvent;
    }
}
