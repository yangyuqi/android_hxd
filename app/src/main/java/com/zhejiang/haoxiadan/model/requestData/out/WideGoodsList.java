package com.zhejiang.haoxiadan.model.requestData.out;

import com.zhejiang.haoxiadan.model.requestData.out.Chose.StoreListBean;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/2.
 */

public class WideGoodsList {
    private List<WideGoodsListBean> goodsList ;
    private List<StoreListBean> storeList ;

    public List<StoreListBean> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<StoreListBean> storeList) {
        this.storeList = storeList;
    }

    public List<WideGoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<WideGoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }
}
