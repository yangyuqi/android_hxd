package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by KK on 2017/9/7.
 */
public class VisualFloorListData {
    private String id;//
    private int sequence;//
    private int isOpen;//
    private String floorName;//
    private String floorType;//
    private int storeId;//
    private String displayMode;//
    private int floorPhotoId;//
    private String floorPhoto;//
    private String decorationType;//
    private String mainFloor;//
    private int parentFloorId;//
    private List<GoodsInfoListData2> goodsInfoList;//
    private String linkedProductType;//
    private String linkedProductUrl;//

    private int goodsSalenum ;

    public int getGoodsSalenum() {
        return goodsSalenum;
    }

    public void setGoodsSalenum(int goodsSalenum) {
        this.goodsSalenum = goodsSalenum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getFloorType() {
        return floorType;
    }

    public void setFloorType(String floorType) {
        this.floorType = floorType;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getDisplayMode() {
        return displayMode;
    }

    public void setDisplayMode(String displayMode) {
        this.displayMode = displayMode;
    }

    public int getFloorPhotoId() {
        return floorPhotoId;
    }

    public void setFloorPhotoId(int floorPhotoId) {
        this.floorPhotoId = floorPhotoId;
    }

    public String getFloorPhoto() {
        return floorPhoto;
    }

    public void setFloorPhoto(String floorPhoto) {
        this.floorPhoto = floorPhoto;
    }

    public String getDecorationType() {
        return decorationType;
    }

    public void setDecorationType(String decorationType) {
        this.decorationType = decorationType;
    }

    public String getMainFloor() {
        return mainFloor;
    }

    public void setMainFloor(String mainFloor) {
        this.mainFloor = mainFloor;
    }

    public int getParentFloorId() {
        return parentFloorId;
    }

    public void setParentFloorId(int parentFloorId) {
        this.parentFloorId = parentFloorId;
    }

    public List<GoodsInfoListData2> getGoodsInfoList() {
        return goodsInfoList;
    }

    public void setGoodsInfoList(List<GoodsInfoListData2> goodsInfoList) {
        this.goodsInfoList = goodsInfoList;
    }

    public String getLinkedProductType() {
        return linkedProductType;
    }

    public void setLinkedProductType(String linkedProductType) {
        this.linkedProductType = linkedProductType;
    }

    public String getLinkedProductUrl() {
        return linkedProductUrl;
    }

    public void setLinkedProductUrl(String linkedProductUrl) {
        this.linkedProductUrl = linkedProductUrl;
    }
}
