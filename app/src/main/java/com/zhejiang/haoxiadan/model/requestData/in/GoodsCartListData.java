package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by KK on 2017/9/1.
 */
public class GoodsCartListData {
    private String goodsId;//	商品id	是	Long
    private String goodsName;//	商家名称	 是	String
    private String goodsPhotoPath;//	商品图片路径	是	String
    private String tierdPrice;//	商品阶梯价	否	string
    private String fightGoodsEndTime;//	商品拼单结束时间	是	String
    private int goodsWhetherCollect;//	是否收藏
    private int goodsNumType;//0  -该商品为期货；1-该商品有库存商品
    private String goodsType;//商品类型（0：普通 1：拼单 ）
    private List<GoodsGspListData> goodsGspList;//	商品规格具体信息集合	是	list
    private String goodsSerial;//商品货号
    private int goodsLimit;//普通商品的最低购买量
    private String goodsSort ;

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

    public String getTierdPrice() {
        return tierdPrice;
    }

    public void setTierdPrice(String tierdPrice) {
        this.tierdPrice = tierdPrice;
    }

    public String getFightGoodsEndTime() {
        return fightGoodsEndTime;
    }

    public void setFightGoodsEndTime(String fightGoodsEndTime) {
        this.fightGoodsEndTime = fightGoodsEndTime;
    }

    public int getGoodsWhetherCollect() {
        return goodsWhetherCollect;
    }

    public void setGoodsWhetherCollect(int goodsWhetherCollect) {
        this.goodsWhetherCollect = goodsWhetherCollect;
    }

    public int getGoodsNumType() {
        return goodsNumType;
    }

    public void setGoodsNumType(int goodsNumType) {
        this.goodsNumType = goodsNumType;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public List<GoodsGspListData> getGoodsGspList() {
        return goodsGspList;
    }

    public void setGoodsGspList(List<GoodsGspListData> goodsGspList) {
        this.goodsGspList = goodsGspList;
    }

    public String getGoodsSerial() {
        return goodsSerial;
    }

    public void setGoodsSerial(String goodsSerial) {
        this.goodsSerial = goodsSerial;
    }

    public int getGoodsLimit() {
        return goodsLimit;
    }

    public void setGoodsLimit(int goodsLimit) {
        this.goodsLimit = goodsLimit;
    }

    public String getGoodsSort() {
        return goodsSort;
    }

    public void setGoodsSort(String goodsSort) {
        this.goodsSort = goodsSort;
    }
}
