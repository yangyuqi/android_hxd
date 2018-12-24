package com.zhejiang.haoxiadan.model.requestData.out.hot;

/**
 * Created by qiuweiyu on 2017/9/4.
 */

public class DataList {
    private String coverPath ;
    private String subtitle ;
    private String title ;
    private int giveUp ;
    private String hotspotInfo ;
    private String type ;
    private int id ;
    private String date ;

    private String url ;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getGiveUp() {
        return giveUp;
    }

    public void setGiveUp(int giveUp) {
        this.giveUp = giveUp;
    }

    public String getHotspotInfo() {
        return hotspotInfo;
    }

    public void setHotspotInfo(String hotspotInfo) {
        this.hotspotInfo = hotspotInfo;
    }
}
