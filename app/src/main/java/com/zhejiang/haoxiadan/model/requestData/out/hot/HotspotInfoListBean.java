package com.zhejiang.haoxiadan.model.requestData.out.hot;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/4.
 */

public class HotspotInfoListBean {
    private List<DataList> hotspotInfoLists ;

    public List<DataList> getDataList() {
        return hotspotInfoLists;
    }

    public void setDataList(List<DataList> dataList) {
        this.hotspotInfoLists = dataList;
    }
}
