package com.zhejiang.haoxiadan.model.requestData.in;

import android.net.LinkAddress;

import java.util.List;

/**
 * Created by KK on 2017/9/5.
 */
public class GoodsClassData {
    private long id;//
    private String className;//
    private IconAccData icon_acc;//
    private String parent_id;//
    private List<GoodsClassData> childs;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public IconAccData getIcon_acc() {
        return icon_acc;
    }

    public void setIcon_acc(IconAccData icon_acc) {
        this.icon_acc = icon_acc;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public List<GoodsClassData> getChilds() {
        return childs;
    }

    public void setChilds(List<GoodsClassData> childs) {
        this.childs = childs;
    }
}
