package com.zhejiang.haoxiadan.model.requestData.out.my;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/13.
 */

public class CollectStoreData {
    private List<CollectStoreDataBean> favoriteList ;
    private int totalPage ;

    public List<CollectStoreDataBean> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(List<CollectStoreDataBean> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
