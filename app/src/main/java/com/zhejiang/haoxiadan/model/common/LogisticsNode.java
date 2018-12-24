package com.zhejiang.haoxiadan.model.common;

/**
 * 物流的节点
 * Created by KK on 2017/8/23.
 */
public class LogisticsNode {
    private String id;
    //内容
    private String content;
    //时间
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
