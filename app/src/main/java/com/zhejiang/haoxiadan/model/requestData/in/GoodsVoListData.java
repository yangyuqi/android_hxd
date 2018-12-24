package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by KK on 2017/9/1.
 */
public class GoodsVoListData {
//    private long storeId;//
//    private String storeName;//
    private String goodsId;//
    private String goodsName;//
    private String goodsPhotoPath;//
    private String fightGoodsEndTime;//
    private List<GoodsGspData> goodsGsp;
//    private double goodsShipPrice;//
//    private double goodsPrice;//
    private String tierdPrice;
    private int goodsType;
    private String goodsNumType;

    public int getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(int goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsNumType() {
        return goodsNumType;
    }

    public void setGoodsNumType(String goodsNumType) {
        this.goodsNumType = goodsNumType;
    }

    public String getTierdPrice() {
        return tierdPrice;
    }

    public void setTierdPrice(String tierdPrice) {
        this.tierdPrice = tierdPrice;
    }



    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPhotoPath() {
        return goodsPhotoPath;
    }

    public void setGoodsPhotoPath(String goodsPhotoPath) {
        this.goodsPhotoPath = goodsPhotoPath;
    }

    public String getFightGoodsEndTime() {
        return fightGoodsEndTime;
    }

    public void setFightGoodsEndTime(String fightGoodsEndTime) {
        this.fightGoodsEndTime = fightGoodsEndTime;
    }

    public List<GoodsGspData> getGoodsGsp() {
        return goodsGsp;
    }

    public void setGoodsGsp(List<GoodsGspData> goodsGsp) {
        this.goodsGsp = goodsGsp;
    }


}
