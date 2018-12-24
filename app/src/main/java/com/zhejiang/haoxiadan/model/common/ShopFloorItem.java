package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;

/**
 * 店铺楼层内item对象
 * Created by KK on 2017/10/8.
 */

public class ShopFloorItem implements Serializable {

    //id
    private String id;

    //标题
    private String title;

    //图片

    private String img;

    //类型
    private FLOOR_ITEM_FORWARD_TYPE forwardType = FLOOR_ITEM_FORWARD_TYPE.NULL;

    //对应去向的ID
    private String contentID;

    //楼层去向的类型
    public enum FLOOR_ITEM_FORWARD_TYPE{
        NULL,
        PRODUCT,    //商品
        CATEGORY,   //商品类型
        CHANNEL,     //频道
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public FLOOR_ITEM_FORWARD_TYPE getForwardType() {
        return forwardType;
    }

    public void setForwardType(FLOOR_ITEM_FORWARD_TYPE forwardType) {
        this.forwardType = forwardType;
    }

    public String getContentID() {
        return contentID;
    }

    public void setContentID(String contentID) {
        this.contentID = contentID;
    }
}
