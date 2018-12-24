package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;
import java.util.List;

/**
 * 类别
 * Created by KK on 2017/8/23.
 */

public class Category  implements Serializable {
    private String id;
    private String name;
    private String icon;
    private List<Category> cates;

    //是否选中
    private boolean isChoose;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Category> getCates() {
        return cates;
    }

    public void setCates(List<Category> cates) {
        this.cates = cates;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
