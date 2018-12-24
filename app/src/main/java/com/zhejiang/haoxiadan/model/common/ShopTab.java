package com.zhejiang.haoxiadan.model.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺的tab
 * Created by KK on 2017/9/7.
 */
public class ShopTab {
    //标题
    private String title;
    //商品列表
    private ArrayList<Goods> goodses;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Goods> getGoodses() {
        return goodses;
    }

    public void setGoodses(ArrayList<Goods> goodses) {
        this.goodses = goodses;
    }
}
