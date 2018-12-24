package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;

/**
 * 火拼规则
 * Created by KK on 2017/8/23.
 */
public class TogetherRule implements Serializable {
    private String id;
    //标题
    private String title;
    //已拼数量
    private String togetherNum;

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

    public String getTogetherNum() {
        return togetherNum;
    }

    public void setTogetherNum(String togetherNum) {
        this.togetherNum = togetherNum;
    }
}
