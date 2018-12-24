package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/5.
 */

public class AreaInfoChildBean {
    private String id ;
    private String areaName ;
    private List<AreaInfoChildBean> childs ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<AreaInfoChildBean> getChilds() {
        return childs;
    }

    public void setChilds(List<AreaInfoChildBean> childs) {
        this.childs = childs;
    }
}
