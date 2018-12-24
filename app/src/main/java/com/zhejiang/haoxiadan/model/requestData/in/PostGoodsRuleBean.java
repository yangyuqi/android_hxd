package com.zhejiang.haoxiadan.model.requestData.in;

import com.zhejiang.haoxiadan.model.requestData.out.TiredPriceData;

import java.util.ArrayList;

/**
 * Created by qiuweiyu on 2017/9/1.
 */

public class PostGoodsRuleBean {
    private ArrayList<TiredPriceData> list ;

    public ArrayList<TiredPriceData> getList() {
        return list;
    }

    public void setList(ArrayList<TiredPriceData> list) {
        this.list = list;
    }
}
