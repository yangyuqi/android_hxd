package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;

/**
 * 协商历史
 * Created by KK on 2017/8/23.
 */
public class ConsultHistory implements Serializable {
    private String id;
    //头像
    private String userIcon;
    //姓名
    private String userName;
    //时间
    private String time;
    //内容
    private String content;
    //备注
    private String extra;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
