package com.zhejiang.haoxiadan.model.requestData.out.Chose;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/15.
 */

public class PrefenceFactoryData {
    private int allPage ;
    private int currentPage ;
    private List<PrefenceFactoryBean> storeList ;

    public int getAllPage() {
        return allPage;
    }

    public void setAllPage(int allPage) {
        this.allPage = allPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<PrefenceFactoryBean> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<PrefenceFactoryBean> storeList) {
        this.storeList = storeList;
    }
}
