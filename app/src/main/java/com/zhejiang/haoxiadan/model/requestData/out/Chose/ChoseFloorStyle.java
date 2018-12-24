package com.zhejiang.haoxiadan.model.requestData.out.Chose;

/**
 * Created by qiuweiyu on 2017/9/12.
 */

public class ChoseFloorStyle {
    private String img ;
    private int goodsId ;
    private int seq ;
    private String goodsPrice ;
    private String collCount ;
    private String goodsName ;
    private String goodsSerial ;
    private String monthlySales ;
    private String gcId ;

    private String goodsNumType ;

    private String value ;
    private String type ;

    private int goodsSalenum ;
    private int goodsSaleNum ;
    private String titleName;

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public int getGoodsSaleNum() {
        return goodsSaleNum;
    }

    public void setGoodsSaleNum(int goodsSaleNum) {
        this.goodsSaleNum = goodsSaleNum;
    }

    public int getGoodsSalenum() {
        return goodsSalenum;
    }

    public void setGoodsSalenum(int goodsSalenum) {
        this.goodsSalenum = goodsSalenum;
    }

    public String getGoodsNumType() {
        return goodsNumType;
    }

    public void setGoodsNumType(String goodsNumType) {
        this.goodsNumType = goodsNumType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(String monthlySales) {
        this.monthlySales = monthlySales;
    }

    private String style ;

    private String storeName ;
    private String storeId ;

    public String getImg() {
        return img;
    }

    public ChoseFloorStyle(String img, int goodsId, int seq, String goodsPrice, String collCount, String goodsName, String goodsSerial, String style, String storeName, String storeId,String monthlySales,String gcId  ,String type ,String value ,String goodsNumType ,int goodsSalenum) {
        this.img = img;
        this.goodsId = goodsId;
        this.seq = seq;
        this.goodsPrice = goodsPrice;
        this.collCount = collCount;
        this.goodsName = goodsName;
        this.goodsSerial = goodsSerial;
        this.style = style;
        this.storeName = storeName;
        this.storeId = storeId;
        this.monthlySales = monthlySales;
        this.gcId = gcId ;
        this.type = type ;
        this.value = value ;
        this.goodsNumType = goodsNumType;
        this.goodsSalenum = goodsSalenum ;
    }



    public String getGcId() {
        return gcId;
    }

    public void setGcId(String gcId) {
        this.gcId = gcId;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getCollCount() {
        return collCount;
    }

    public void setCollCount(String collCount) {
        this.collCount = collCount;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSerial() {
        return goodsSerial;
    }

    public void setGoodsSerial(String goodsSerial) {
        this.goodsSerial = goodsSerial;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
