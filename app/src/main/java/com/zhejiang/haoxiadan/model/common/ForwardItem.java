package com.zhejiang.haoxiadan.model.common;

/**
 * 不定向跳转的item，banner\广告栏 等
 * Created by KK on 2017/8/23.
 */
public class ForwardItem {
    private String id;
    private String title;
    private String photo;
    private FORWARD_TYPE forwardType = FORWARD_TYPE.NULL;
    private String forwardId;

    public enum FORWARD_TYPE{
        NULL,
        GOODS,  //商品详情
        GOODS_LIST   //商品列表
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public FORWARD_TYPE getForwardType() {
        return forwardType;
    }

    public void setForwardType(FORWARD_TYPE forwardType) {
        this.forwardType = forwardType;
    }

    public String getForwardId() {
        return forwardId;
    }

    public void setForwardId(String forwardId) {
        this.forwardId = forwardId;
    }
}
