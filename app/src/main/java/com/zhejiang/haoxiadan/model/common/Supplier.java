package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;

/**
 * 供应商
 * Created by KK on 2017/8/23.
 */
public class Supplier implements Serializable{
    private String id;
    private String icon;//供应商图标
    private String shopId;//店铺id
    private String company;//	公司名称
    private String licenseNumber;//	营业执照号
    private String enterpriseType;//	企业类型
    private String employeesNum;//	员工人数
    private String mainIndustryId;//	主营行业Id
    private String mainIndustry;//	主营行业
    private String productStyleId;//	产品风格id
    private String productStyle;//	产品风格
    private String mainProducts;//	主营产品
    private String mainCustomerGroups;//	主要客户群体
    private String yearSales;//	年销售额
    private String quId;//	区id
    private String area;
    private String detailAddress;//	详细地址
    private String businessContacts;//	业务联系人
    private String contactPhone;//	联系人电话
    private String shopIntroduction;//	店铺介绍
    private String monthProduction;//	月产量
    private String yearExportNum;//	年出口额

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(String enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    public String getEmployeesNum() {
        return employeesNum;
    }

    public void setEmployeesNum(String employeesNum) {
        this.employeesNum = employeesNum;
    }

    public String getMainIndustryId() {
        return mainIndustryId;
    }

    public void setMainIndustryId(String mainIndustryId) {
        this.mainIndustryId = mainIndustryId;
    }

    public String getMainIndustry() {
        return mainIndustry;
    }

    public void setMainIndustry(String mainIndustry) {
        this.mainIndustry = mainIndustry;
    }

    public String getProductStyleId() {
        return productStyleId;
    }

    public void setProductStyleId(String productStyleId) {
        this.productStyleId = productStyleId;
    }

    public String getProductStyle() {
        return productStyle;
    }

    public void setProductStyle(String productStyle) {
        this.productStyle = productStyle;
    }

    public String getMainProducts() {
        return mainProducts;
    }

    public void setMainProducts(String mainProducts) {
        this.mainProducts = mainProducts;
    }

    public String getMainCustomerGroups() {
        return mainCustomerGroups;
    }

    public void setMainCustomerGroups(String mainCustomerGroups) {
        this.mainCustomerGroups = mainCustomerGroups;
    }

    public String getYearSales() {
        return yearSales;
    }

    public void setYearSales(String yearSales) {
        this.yearSales = yearSales;
    }

    public String getQuId() {
        return quId;
    }

    public void setQuId(String quId) {
        this.quId = quId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getBusinessContacts() {
        return businessContacts;
    }

    public void setBusinessContacts(String businessContacts) {
        this.businessContacts = businessContacts;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getShopIntroduction() {
        return shopIntroduction;
    }

    public void setShopIntroduction(String shopIntroduction) {
        this.shopIntroduction = shopIntroduction;
    }

    public String getMonthProduction() {
        return monthProduction;
    }

    public void setMonthProduction(String monthProduction) {
        this.monthProduction = monthProduction;
    }

    public String getYearExportNum() {
        return yearExportNum;
    }

    public void setYearExportNum(String yearExportNum) {
        this.yearExportNum = yearExportNum;
    }
}
