package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;
import java.util.Map;

/**
 * Created by qiuweiyu on 2017/9/5.
 */

public class AreaInfoBean {
    private List<AreaInfoChildBean> areaList ;



    public List<AreaInfoChildBean> getData() {
        return areaList;
    }

    public void setData(List<AreaInfoChildBean> data) {
        this.areaList = data;
    }

    public AreaInfoBean(List<AreaInfoChildBean> data) {

        this.areaList = data;
    }
}
