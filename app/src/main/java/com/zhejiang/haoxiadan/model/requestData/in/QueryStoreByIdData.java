package com.zhejiang.haoxiadan.model.requestData.in;

/**
 * Created by KK on 2017/9/7.
 */
public class QueryStoreByIdData {
    private long storeId;//	店铺id	是	String
    private String storeName;//	店铺名	是	String
    private String storeInfo;//	店铺简介	是	String
    private String storeLogo;//	店铺logo	是
    private String licenseAddress;//	公司详细地址	是
    private String storeTelephoneas;//	店铺电话号码	是
    private String storeAddress;//	店铺详细地址	是
    private String mainIndustry;//	主营行业	是
    private String mainProducts;//	主营商品	是
    private long salesVolume;//	商家销量	是
    private String licenseNum;//		是
    private String isCollect;//		否
    private String storePhotoBg;//

    private String storeArea ;

    public String getStoreArea() {
        return storeArea;
    }

    public void setStoreArea(String storeArea) {
        this.storeArea = storeArea;
    }

    public String getStorePhotoBg() {
        return storePhotoBg;
    }

    public void setStorePhotoBg(String storePhotoBg) {
        this.storePhotoBg = storePhotoBg;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(String storeInfo) {
        this.storeInfo = storeInfo;
    }

    public String getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }

    public String getLicenseAddress() {
        return licenseAddress;
    }

    public void setLicenseAddress(String licenseAddress) {
        this.licenseAddress = licenseAddress;
    }

    public String getStoreTelephoneas() {
        return storeTelephoneas;
    }

    public void setStoreTelephoneas(String storeTelephoneas) {
        this.storeTelephoneas = storeTelephoneas;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getMainIndustry() {
        return mainIndustry;
    }

    public void setMainIndustry(String mainIndustry) {
        this.mainIndustry = mainIndustry;
    }

    public String getMainProducts() {
        return mainProducts;
    }

    public void setMainProducts(String mainProducts) {
        this.mainProducts = mainProducts;
    }

    public long getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(long salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getLicenseNum() {
        return licenseNum;
    }

    public void setLicenseNum(String licenseNum) {
        this.licenseNum = licenseNum;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }
}
