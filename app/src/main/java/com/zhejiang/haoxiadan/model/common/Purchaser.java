package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;

/**
 * 采购商
 * Created by KK on 2017/8/23.
 */
public class Purchaser implements Serializable{
    private String id;
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
    private String mainSalesChannels;//	主要销售渠道
    private String monthSales;//	月销售额
    private String yearSales;//	年销售额
    private String quId;//	区id
    private String area;
    private String detailAddress;//	详细地址
    private String businessContacts;//	业务联系人
    private String contactPhone;//	联系人电话

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getMainSalesChannels() {
        return mainSalesChannels;
    }

    public void setMainSalesChannels(String mainSalesChannels) {
        this.mainSalesChannels = mainSalesChannels;
    }

    public String getMonthSales() {
        return monthSales;
    }

    public void setMonthSales(String monthSales) {
        this.monthSales = monthSales;
    }
}
