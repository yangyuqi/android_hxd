package com.zhejiang.haoxiadan.model.common;

/**
 * Created by KK on 2017/9/27 .
 */
public class VersionManageData {
    private String versionNum;
    private String versionSource;
    private int isForceUpdate;
    private String versionLinkUrl;

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public String getVersionSource() {
        return versionSource;
    }

    public void setVersionSource(String versionSource) {
        this.versionSource = versionSource;
    }

    public int getIsForceUpdate() {
        return isForceUpdate;
    }

    public void setIsForceUpdate(int isForceUpdate) {
        this.isForceUpdate = isForceUpdate;
    }

    public String getVersionLinkUrl() {
        return versionLinkUrl;
    }

    public void setVersionLinkUrl(String versionLinkUrl) {
        this.versionLinkUrl = versionLinkUrl;
    }
}
