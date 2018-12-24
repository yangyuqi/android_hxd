package com.zhejiang.haoxiadan.model.common;

import java.util.List;

/**
 * 区域
 * Created by KK on 2017/8/23.
 */
public class Area {
    private String id;
    //名字
    private String name;
    //下级区域
    private List<Area> areas;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    @Override
    public String toString() {
        return name;
    }
}
