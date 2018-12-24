package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;

/**
 * 热点
 * Created by KK on 2017/8/23.
 */
public class Hot implements Serializable {
    private String id;
    //标题
    private String title;
    //内容
    private String content;
    //图文详情
    private String detail;
    //点赞数量
    private int likeNum;
    //日期
    private String time;
    //星期
    private String week;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
