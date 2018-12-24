package com.zhejiang.haoxiadan.model.requestData.out;

import com.zhejiang.haoxiadan.model.choseModel.ModelValBean;
import com.zhejiang.haoxiadan.model.choseModel.PropertyModelItem;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.GoodsGspPhotoDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/1.
 */

public class GetGoodsBean {
    private int id ;
    private int goodsStatus ;
    private String goodsMainPhoto ;
    private int goodsWhetherCollect ;
    private String goodsName ;
    private double storePrice ;
    private String goodsSerial ;
    private int evaluateCount ;
    private int storeId ;
    private int goodsSalenum ;
    private int goodsClick ;
    private long fightGoodsEndTime ;
    private List<GetGoodsIntData> goodsInvenDetail ;
    private ArrayList<TiredPriceData> tierdPriceAll ;
    private int crowdfundingCount ;
    private int goodsNumType ;
    private int goods_collect ;
    private String goods_details ;

    private List<GetGoodsPhotoData> goodsPhotos ;
    private String storeArea ;
    private String features ;

    private List<GoodsGspPhotoDetail> goodsGspPhotoDetail ;
    private List<String> goodsInfoPhotePath ;

    private String goodsType ;
    private int monthlySales ;
    private int checkOutCount ;
    private List<ModelValBean> featureObj ;
    private String supplyMode ;
    private String classNameThree ;

    private int goodsLimit ;

    private String replaceType ;
    private int saleMode ;
    private int sampleService ;
    private String returnGoods ;
    private int materal ;
    private int matched ;
    private int pictureType ;
    private int machCycle ;
    private String shipTime ;
    private int editService ;

    public int getGoodsLimit() {
        return goodsLimit;
    }

    public void setGoodsLimit(int goodsLimit) {
        this.goodsLimit = goodsLimit;
    }

    public int getEditService() {
        return editService;
    }

    public void setEditService(int editService) {
        this.editService = editService;
    }

    public String getShipTime() {
        return shipTime;
    }

    public void setShipTime(String shipTime) {
        this.shipTime = shipTime;
    }

    public int getMachCycle() {
        return machCycle;
    }

    public void setMachCycle(int machCycle) {
        this.machCycle = machCycle;
    }

    private List<PropertyModelItem> productParamList ;

    public String getReplaceType() {
        return replaceType;
    }

    public void setReplaceType(String replaceType) {
        this.replaceType = replaceType;
    }

    public int getSaleMode() {
        return saleMode;
    }

    public void setSaleMode(int saleMode) {
        this.saleMode = saleMode;
    }

    public int getSampleService() {
        return sampleService;
    }

    public void setSampleService(int sampleService) {
        this.sampleService = sampleService;
    }

    public String getReturnGoods() {
        return returnGoods;
    }

    public void setReturnGoods(String returnGoods) {
        this.returnGoods = returnGoods;
    }

    public int getMateral() {
        return materal;
    }

    public void setMateral(int materal) {
        this.materal = materal;
    }

    public int getMatched() {
        return matched;
    }

    public void setMatched(int matched) {
        this.matched = matched;
    }

    public int getPictureType() {
        return pictureType;
    }

    public void setPictureType(int pictureType) {
        this.pictureType = pictureType;
    }

    public int getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(int monthlySales) {
        this.monthlySales = monthlySales;
    }

    public int getCheckOutCount() {
        return checkOutCount;
    }

    public void setCheckOutCount(int checkOutCount) {
        this.checkOutCount = checkOutCount;
    }

    public List<ModelValBean> getFeatureObj() {
        return featureObj;
    }

    public void setFeatureObj(List<ModelValBean> featureObj) {
        this.featureObj = featureObj;
    }

    public String getSupplyMode() {
        return supplyMode;
    }

    public void setSupplyMode(String supplyMode) {
        this.supplyMode = supplyMode;
    }

    public String getClassNameThree() {
        return classNameThree;
    }

    public void setClassNameThree(String classNameThree) {
        this.classNameThree = classNameThree;
    }

    public List<GoodsGspPhotoDetail> getGoodsGspPhotoDetail() {
        return goodsGspPhotoDetail;
    }

    public void setGoodsGspPhotoDetail(List<GoodsGspPhotoDetail> goodsGspPhotoDetail) {
        this.goodsGspPhotoDetail = goodsGspPhotoDetail;
    }

    public List<String> getGoodsInfoPhotePath() {
        return goodsInfoPhotePath;
    }

    public void setGoodsInfoPhotePath(List<String> goodsInfoPhotePath) {
        this.goodsInfoPhotePath = goodsInfoPhotePath;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsMainPhoto() {
        return goodsMainPhoto;
    }

    public void setGoodsMainPhoto(String goodsMainPhoto) {
        this.goodsMainPhoto = goodsMainPhoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(int goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public String getGoods_main_photo() {
        return goodsMainPhoto;
    }

    public void setGoods_main_photo(String goods_main_photo) {
        this.goodsMainPhoto = goods_main_photo;
    }

    public int getGoodsWhetherCollect() {
        return goodsWhetherCollect;
    }

    public void setGoodsWhetherCollect(int goodsWhetherCollect) {
        this.goodsWhetherCollect = goodsWhetherCollect;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getStorePrice() {
        return storePrice;
    }

    public void setStorePrice(double storePrice) {
        this.storePrice = storePrice;
    }

    public String getGoodsSerial() {
        return goodsSerial;
    }

    public void setGoodsSerial(String goodsSerial) {
        this.goodsSerial = goodsSerial;
    }

    public int getEvaluateCount() {
        return evaluateCount;
    }

    public void setEvaluateCount(int evaluateCount) {
        this.evaluateCount = evaluateCount;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getGoodsSalenum() {
        return goodsSalenum;
    }

    public void setGoodsSalenum(int goodsSalenum) {
        this.goodsSalenum = goodsSalenum;
    }

    public int getGoodsClick() {
        return goodsClick;
    }

    public void setGoodsClick(int goodsClick) {
        this.goodsClick = goodsClick;
    }

    public long getFightGoodsEndTime() {
        return fightGoodsEndTime;
    }

    public void setFightGoodsEndTime(long fightGoodsEndTime) {
        this.fightGoodsEndTime = fightGoodsEndTime;
    }

    public List<GetGoodsIntData> getGoodsInvenDetail() {
        return goodsInvenDetail;
    }

    public void setGoodsInvenDetail(List<GetGoodsIntData> goodsInvenDetail) {
        this.goodsInvenDetail = goodsInvenDetail;
    }

    public ArrayList<TiredPriceData> getTierdPriceAll() {
        return tierdPriceAll;
    }

    public void setTierdPriceAll(ArrayList<TiredPriceData> tierdPriceAll) {
        this.tierdPriceAll = tierdPriceAll;
    }

    public int getCrowdfundingCount() {
        return crowdfundingCount;
    }

    public void setCrowdfundingCount(int crowdfundingCount) {
        this.crowdfundingCount = crowdfundingCount;
    }

    public int getGoodsNumType() {
        return goodsNumType;
    }

    public void setGoodsNumType(int goodsNumType) {
        this.goodsNumType = goodsNumType;
    }

    public int getGoods_collect() {
        return goods_collect;
    }

    public void setGoods_collect(int goods_collect) {
        this.goods_collect = goods_collect;
    }

    public String getGoods_details() {
        return goods_details;
    }

    public void setGoods_details(String goods_details) {
        this.goods_details = goods_details;
    }


    public List<GetGoodsPhotoData> getGoodsPhotos() {
        return goodsPhotos;
    }

    public void setGoodsPhotos(List<GetGoodsPhotoData> goodsPhotos) {
        this.goodsPhotos = goodsPhotos;
    }

    public String getStoreArea() {
        return storeArea;
    }

    public void setStoreArea(String storeArea) {
        this.storeArea = storeArea;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public List<PropertyModelItem> getProductParamList() {
        return productParamList;
    }

    public void setProductParamList(List<PropertyModelItem> productParamList) {
        this.productParamList = productParamList;
    }
}
