package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by KK on 2017/9/4.
 */
public class SelectAreaAllData {
    private String id;//	区域id	是	String
    private String areaName;//	收货人姓名	是	String
    private List<SelectAreaAllData> childs;//	收货人详细地址	是	List<Area>		子集，共三级（一级省和直辖市，二级市，三区）

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

    public List<SelectAreaAllData> getChilds() {
        return childs;
    }

    public void setChilds(List<SelectAreaAllData> childs) {
        this.childs = childs;
    }
}
