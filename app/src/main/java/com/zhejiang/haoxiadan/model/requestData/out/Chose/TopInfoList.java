package com.zhejiang.haoxiadan.model.requestData.out.Chose;

import com.zhejiang.haoxiadan.model.requestData.out.hot.DataList;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/8.
 */

public class TopInfoList {
    private List<TopInfoListBean> topInfoList ;

    public List<TopInfoListBean> getTopInfoList() {
        return topInfoList;
    }

    public void setTopInfoList(List<TopInfoListBean> topInfoList) {
        this.topInfoList = topInfoList;
    }
}
