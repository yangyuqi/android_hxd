package com.zhejiang.haoxiadan.model.requestData.out;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/1.
 */

public class CollectGoodsBeanData {
    private int totalPage ;
    private List<CollectGoodsBean> favoriteList ;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<CollectGoodsBean> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(List<CollectGoodsBean> favoriteList) {
        this.favoriteList = favoriteList;
    }
}
