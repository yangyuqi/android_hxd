package com.zhejiang.haoxiadan.model.requestData.in;

/**
 * Created by KK on 2017/9/7.
 */
public class QueryStoreArchivesData {
    private long id;//	商家id	是	String
    private long storeUserId;//	商家用户id	是	String
    private String company;//	公司名称	是	String
    private String store_info;//	店铺简介	是	Int
    private String mainIndustry;//	主营行业	是
    private String mainProducts;//	主营产品	是
    private String productStyle;//	产品风格	是
    private String mainCustomerGroups;//	主要客户群体	是
    private String enterpriseType;//	企业类型	是
    private String yearExportNum;//	年出口额	是
    private String licenseNumber;//	营业执照号	是
    private String yearSales;//	年销售额	是
    private String employeesNum;//	人数	是
    private String monthProduction;//	月产量	是

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStoreUserId() {
        return storeUserId;
    }

    public void setStoreUserId(long storeUserId) {
        this.storeUserId = storeUserId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStore_info() {
        return store_info;
    }

    public void setStore_info(String store_info) {
        this.store_info = store_info;
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

    public String getProductStyle() {
        return productStyle;
    }

    public void setProductStyle(String productStyle) {
        this.productStyle = productStyle;
    }

    public String getMainCustomerGroups() {
        return mainCustomerGroups;
    }

    public void setMainCustomerGroups(String mainCustomerGroups) {
        this.mainCustomerGroups = mainCustomerGroups;
    }

    public String getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(String enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    public String getYearExportNum() {
        return yearExportNum;
    }

    public void setYearExportNum(String yearExportNum) {
        this.yearExportNum = yearExportNum;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getYearSales() {
        return yearSales;
    }

    public void setYearSales(String yearSales) {
        this.yearSales = yearSales;
    }

    public String getEmployeesNum() {
        return employeesNum;
    }

    public void setEmployeesNum(String employeesNum) {
        this.employeesNum = employeesNum;
    }

    public String getMonthProduction() {
        return monthProduction;
    }

    public void setMonthProduction(String monthProduction) {
        this.monthProduction = monthProduction;
    }
}
