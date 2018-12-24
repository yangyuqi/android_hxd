package com.zhejiang.haoxiadan.model.common;

/**
 * 系统消息
 * Created by KK on 2017/8/23.
 */
public class SystemMessage {
    private String id;
    //图标
    private String icon;
    //标题
    private String title;
    //内容
    private String content;
    //订单号
    private String orderNo;
    //时间
    private String time;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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
