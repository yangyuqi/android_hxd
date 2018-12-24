package com.zhejiang.haoxiadan.model.requestData.in;

/**
 * Created by KK on 2017/9/4.
 */
public class RoleApplyData {

    private String id;//		否	String		修改时有此参数
    private String company;//	公司名称	是	String
    private String licenseNumber;//	营业执照号	是	String
    private String enterpriseType;//	企业类型	是	String
    private String employeesNum;//	员工人数	是	String
    private String mainIndustryId;//	主营行业Id	是	String
    private String mainIndustry;//	主营行业	是	String
    private String productStyleId;//	产品风格id	是	String
    private String productStyle;//	产品风格	是	String
    private String mainProducts;//	主营产品	是	String
    private String mainCustomerGroups;//	主要客户群体	是	String
    private String yearSales;//	年销售额	是	String
    private String shengId;//	省id	是	String
    private String shiId;//	市id	是	String
    private String quId;//	区id	是	String
    private String area;//	所在地	是	String
    private String detailAddress;//	详细地址	是	String
    private String businessContacts;//	业务联系人	是	String
    private String contactPhone;//	联系人电话	是	String
    private String mainSalesChannels;//	主要销售渠道	否	String		采购商有此参数
    private String monthSales;//	月销售额	否	String		采购商有此参数
    private String shopIntroduction;//	店铺介绍	否	String		供应商有此参数
    private String monthProduction;//	月产量	否	String		供应商有此参数
    private String yearExportNum;//	年出口额	否	String		供应商有此参数

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

    public String getShengId() {
        return shengId;
    }

    public void setShengId(String shengId) {
        this.shengId = shengId;
    }

    public String getShiId() {
        return shiId;
    }

    public void setShiId(String shiId) {
        this.shiId = shiId;
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
