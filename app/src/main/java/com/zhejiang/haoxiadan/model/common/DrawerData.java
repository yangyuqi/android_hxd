package com.zhejiang.haoxiadan.model.common;

/**
 * 侧边栏
 * Created by KK on 2017/2/22.
 */

public class DrawerData {
    private String id;
    private String icon;
    private String title;
    private FORWARD_TYPE forwardType = FORWARD_TYPE.NULL;


    public FORWARD_TYPE getForwardType() {
        return forwardType;
    }

    public void setForwardType(FORWARD_TYPE forwardType) {
        this.forwardType = forwardType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
