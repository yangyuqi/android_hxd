package com.zhejiang.haoxiadan.model.common;

/**
 * 消息
 * Created by KK on 2017/8/23.
 */
public class Message {
    private String id;
    //消息类型
    private MESSAGE_TYPE messageType;
    //图标
    private String icon;
    //标题
    private String title;
    //内容
    private String content;
    //时间
    private String time;

    public enum MESSAGE_TYPE{
        IM, //即时通讯的消息
        SYSTEM //系统消息
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MESSAGE_TYPE getMessageType() {
        return messageType;
    }

    public void setMessageType(MESSAGE_TYPE messageType) {
        this.messageType = messageType;
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
