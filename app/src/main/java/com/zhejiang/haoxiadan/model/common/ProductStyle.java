package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;

/**
 * 产品风格
 * Created by KK on 2017/8/23.
 */
public class ProductStyle implements Serializable {
    private String id;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
