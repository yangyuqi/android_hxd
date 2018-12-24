package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;

/**
 * 原因
 * Created by KK on 2017/9/7.
 */
public class Reason implements Serializable {
    private String id;
    private String title;

    @Override
    public String toString() {
        return title;
    }

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
}
