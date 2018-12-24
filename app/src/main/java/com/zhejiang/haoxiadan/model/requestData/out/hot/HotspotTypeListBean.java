package com.zhejiang.haoxiadan.model.requestData.out.hot;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/4.
 */

public class HotspotTypeListBean {
    private String typeName ;
    private List<HotspotInfoListBean> hotspotInfoList ;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<HotspotInfoListBean> getHotspotInfoList() {
        return hotspotInfoList;
    }

    public void setHotspotInfoList(List<HotspotInfoListBean> hotspotInfoList) {
        this.hotspotInfoList = hotspotInfoList;
    }
}
