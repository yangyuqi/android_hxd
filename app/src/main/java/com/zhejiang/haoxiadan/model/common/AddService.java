package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;

/**
 * 增值服务
 * Created by KK on 2017/8/23.
 */
public class AddService implements Serializable {
    private String id;
    //申请状态
    private SERVICE_STATUS serviceStatus = SERVICE_STATUS.NOT_APPLY;
    //申请类型
    private SERVICE_TYPE serviceType;
    //供应商
    private Supplier supplier;
    //采购商
    private Purchaser purchaser;
    //有效期
    private String validDate;

    public enum SERVICE_TYPE{
        SUPPLIER,//供应商
        PURCHASER,//采购商
    }
    public enum SERVICE_STATUS{
        NOT_APPLY,//未申请
        NOT_PAY,//未支付
        SUCCESS,//通过
        OVERDUE,//过期
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SERVICE_STATUS getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(SERVICE_STATUS serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public SERVICE_TYPE getServiceType() {
        return serviceType;
    }

    public void setServiceType(SERVICE_TYPE serviceType) {
        this.serviceType = serviceType;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Purchaser getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(Purchaser purchaser) {
        this.purchaser = purchaser;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }
}
