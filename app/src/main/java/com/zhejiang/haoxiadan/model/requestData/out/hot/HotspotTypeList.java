package com.zhejiang.haoxiadan.model.requestData.out.hot;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/4.
 */

public class HotspotTypeList {
    private List<TypeListBean> hotspotTypeLists ;

    public List<TypeListBean> getHotspotTypeList() {
        return hotspotTypeLists;
    }

    public void setHotspotTypeList(List<TypeListBean> hotspotTypeList) {
        this.hotspotTypeLists = hotspotTypeList;
    }
}
