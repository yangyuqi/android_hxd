package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.business.request.cart.BalanceStatusListener;
import com.zhejiang.haoxiadan.model.common.Purchaser;
import com.zhejiang.haoxiadan.model.common.Supplier;
import com.zhejiang.haoxiadan.model.requestData.in.ApplyRoleInfoData;
import com.zhejiang.haoxiadan.model.requestData.in.RoleApplyData;
import com.zhejiang.haoxiadan.ui.activity.my.ValueAddActivity;

/**
 * 采购商/供应商申请信息
 * Created by KK on 2017/9/4.
 */
public class ApplyRoleInfoParser extends AbsBaseParser {

    public ApplyRoleInfoParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        ValueAddActivity.VALUE_ADD_STATUS status = null;
        ValueAddActivity.VALUE_ADD_TYPE type = null;
        Purchaser purchaser = null;
        Supplier supplier = null;
        String validityDate = null;

        ApplyRoleInfoData applyRoleInfoData = mDataParser.parseObject(data,ApplyRoleInfoData.class);
        switch (applyRoleInfoData.getStatus()){
            case "not_apply":
                status = ValueAddActivity.VALUE_ADD_STATUS.NOT_APPLY;
                break;
            case "not_pay":
                status = ValueAddActivity.VALUE_ADD_STATUS.NOT_PAY;
                switch (applyRoleInfoData.getType()){
                    case "purchaser":
                        type = ValueAddActivity.VALUE_ADD_TYPE.PURCHASER;
                        purchaser = mappedPurchaser(applyRoleInfoData.getRoleApply());
                        break;
                    case "supplier":
                        type = ValueAddActivity.VALUE_ADD_TYPE.SUPPLIER;
                        supplier = mappedSupplier(applyRoleInfoData.getRoleApply());
                        break;
                }
                break;
            case "renew_pay":
            case "review"://不存在审核中
                status = ValueAddActivity.VALUE_ADD_STATUS.REVIEW;
                switch (applyRoleInfoData.getType()){
                    case "supplier":
                        type = ValueAddActivity.VALUE_ADD_TYPE.SUPPLIER;
                        supplier = mappedSupplier(applyRoleInfoData.getRoleApply());
                        break;
                }
                validityDate = applyRoleInfoData.getDate();
                break;
            case "success":
                status = ValueAddActivity.VALUE_ADD_STATUS.SUCCESS;
                switch (applyRoleInfoData.getType()){
                    case "supplier":
                        type = ValueAddActivity.VALUE_ADD_TYPE.SUPPLIER;
                        supplier = mappedSupplier(applyRoleInfoData.getRoleApply());
                        break;
                }
                validityDate = applyRoleInfoData.getDate();
                break;
            case "failed"://不存在失败
                status = ValueAddActivity.VALUE_ADD_STATUS.FAILED;
                switch (applyRoleInfoData.getType()){
//                    case "purchaser":
//                        type = ValueAddActivity.VALUE_ADD_TYPE.PURCHASER;
//                        purchaser = mappedPurchaser(applyRoleInfoData.getRoleApply());
//                        break;
                    case "supplier":
                        type = ValueAddActivity.VALUE_ADD_TYPE.SUPPLIER;
                        supplier = mappedSupplier(applyRoleInfoData.getRoleApply());
                        break;
                }
                validityDate = applyRoleInfoData.getDate();
                break;
            case "overdue":
                status = ValueAddActivity.VALUE_ADD_STATUS.OVERDUE;
                switch (applyRoleInfoData.getType()){
                    case "purchaser":
                        type = ValueAddActivity.VALUE_ADD_TYPE.PURCHASER;
                        purchaser = mappedPurchaser(applyRoleInfoData.getRoleApply());
                        break;
                    case "supplier":
                        type = ValueAddActivity.VALUE_ADD_TYPE.SUPPLIER;
                        supplier = mappedSupplier(applyRoleInfoData.getRoleApply());
                        break;
                }
                validityDate = applyRoleInfoData.getDate();
                break;
        }


        ApplyRoleInfoListener listener = (ApplyRoleInfoListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onApplyRoleInfoSuccess(status,type,validityDate,purchaser,supplier);
        }
    }

    private Purchaser mappedPurchaser(RoleApplyData roleApplyData){
        Purchaser purchaser = new Purchaser();
        purchaser.setId(roleApplyData.getId());
        purchaser.setCompany(roleApplyData.getCompany());
        purchaser.setLicenseNumber(roleApplyData.getLicenseNumber());
        purchaser.setEnterpriseType(roleApplyData.getEnterpriseType());
        purchaser.setEmployeesNum(roleApplyData.getEmployeesNum());
        purchaser.setMainIndustryId(roleApplyData.getMainIndustryId());
        purchaser.setMainIndustry(roleApplyData.getMainIndustry());
        purchaser.setProductStyleId(roleApplyData.getProductStyleId());
        purchaser.setProductStyle(roleApplyData.getProductStyle());
        purchaser.setMainProducts(roleApplyData.getMainProducts());
        purchaser.setMainCustomerGroups(roleApplyData.getMainCustomerGroups());
        purchaser.setMainSalesChannels(roleApplyData.getMainSalesChannels());
        purchaser.setMonthSales(roleApplyData.getMonthSales());
        purchaser.setYearSales(roleApplyData.getYearSales());
        purchaser.setQuId(roleApplyData.getQuId());
        purchaser.setArea(roleApplyData.getArea());
        purchaser.setDetailAddress(roleApplyData.getDetailAddress());
        purchaser.setBusinessContacts(roleApplyData.getBusinessContacts());
        purchaser.setContactPhone(roleApplyData.getContactPhone());

        return purchaser;
    }

    private Supplier mappedSupplier(RoleApplyData roleApplyData){
        Supplier supplier = new Supplier();
        supplier.setId(roleApplyData.getId());
        supplier.setCompany(roleApplyData.getCompany());
        supplier.setLicenseNumber(roleApplyData.getLicenseNumber());
        supplier.setEnterpriseType(roleApplyData.getEnterpriseType());
        supplier.setEmployeesNum(roleApplyData.getEmployeesNum());
        supplier.setMainIndustryId(roleApplyData.getMainIndustryId());
        supplier.setMainIndustry(roleApplyData.getMainIndustry());
        supplier.setProductStyleId(roleApplyData.getProductStyleId());
        supplier.setProductStyle(roleApplyData.getProductStyle());
        supplier.setMainProducts(roleApplyData.getMainProducts());
        supplier.setMainCustomerGroups(roleApplyData.getMainCustomerGroups());
        supplier.setShopIntroduction(roleApplyData.getShopIntroduction());
        supplier.setMonthProduction(roleApplyData.getMonthProduction());
        supplier.setYearSales(roleApplyData.getYearSales());
        supplier.setYearExportNum(roleApplyData.getYearExportNum());
        supplier.setQuId(roleApplyData.getQuId());
        supplier.setArea(roleApplyData.getArea());
        supplier.setDetailAddress(roleApplyData.getDetailAddress());
        supplier.setBusinessContacts(roleApplyData.getBusinessContacts());
        supplier.setContactPhone(roleApplyData.getContactPhone());

        return supplier;
    }

}
