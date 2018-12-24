package com.zhejiang.haoxiadan.model.common;

/**
 * Created by wqd on 2017/9/7 0007.
 */

public class PushInfo {
    private String id;
    private String urlType;
    private String urlTypeId;
    private String urlTypeTitle;
    private String addTime;
    private String pushContentTitle;
    private String pushContent;
    private String readStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getUrlTypeId() {
        return urlTypeId;
    }

    public void setUrlTypeId(String urlTypeId) {
        this.urlTypeId = urlTypeId;
    }

    public String getUrlTypeTitle() {
        return urlTypeTitle;
    }

    public void setUrlTypeTitle(String urlTypeTitle) {
        this.urlTypeTitle = urlTypeTitle;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getPushContentTitle() {
        return pushContentTitle;
    }

    public void setPushContentTitle(String pushContentTitle) {
        this.pushContentTitle = pushContentTitle;
    }

    public String getPushContent() {
        return pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }
}
