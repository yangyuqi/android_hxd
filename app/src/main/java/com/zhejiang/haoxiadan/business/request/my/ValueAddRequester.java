package com.zhejiang.haoxiadan.business.request.my;

import android.content.Context;

import com.zhejiang.haoxiadan.business.BaseRequester;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.common.Purchaser;
import com.zhejiang.haoxiadan.model.common.Supplier;
import com.zhejiang.haoxiadan.third.network.Server;
import com.zhejiang.haoxiadan.ui.activity.my.ValueAddActivity;
import com.zhejiang.haoxiadan.util.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 增值服务
 * Created by KK on 2017/9/4.
 */
public class ValueAddRequester extends BaseRequester {

    /**
     * 采购商/供应商申请信息
     * @param context
     * @param accessToken
     * @param listener
     */
    public void applyRoleInfo(Context context, String accessToken, ApplyRoleInfoListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        post(context, Server.getUrl("valueAdd/applyRoleInfo"),params,listener,new ApplyRoleInfoParser(listener));
    }

    /**
     * 行业表
     * @param context
     * @param listener
     */
    public void queryTradeInfo(Context context,QueryTradeInfoListener listener){
        Map<String, Object> params = new HashMap<>();
        String token = (String)SharedPreferencesUtil.get(context, Constants.accessToken,"");
        if(!"".equals(token)){
            params.put("accessToken",token);
        }
        post(context, Server.getUrl("trade/queryTradeInfo"),params,listener,new QueryTradeInfoParser(listener));
    }

    /**
     * 用户关注行业信息新增
     * @param context
     * @param industryIds
     * @param type
     * @param listener
     */
    public void insertUserTradeInfo(Context context, List<String> industryIds,int type, InsertUserTradeInfoListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",SharedPreferencesUtil.get(context, Constants.accessToken,""));
        params.put("tradeIds",industryIds);
        params.put("type",type);
        post(context, Server.getUrl("trade/insertUserTradeInfo"),params,listener,new InsertUserTradeInfoParser(listener));
    }

    /**
     * 获取所有区域接口
     * @param context
     * @param listener
     */
    public void selectAreaAll(Context context,SelectAreaAllListener listener){
        Map<String, Object> params = new HashMap<>();
        post(context, Server.getUrl("area/selectAreaAll"),params,listener,new SelectAreaAllParser(listener));
    }

    /**
     * 采购商/供应商申请
     * @param context
     * @param type
     * @param purchaser
     * @param supplier
     * @param listener
     */
    public void applyRole(Context context, String accessToken, ValueAddActivity.VALUE_ADD_TYPE type, Purchaser purchaser, Supplier supplier, ApplyRoleListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        switch (type){
            case PURCHASER:
                params.put("type","purchaser");
                params.put("id",purchaser.getId());
                params.put("company",purchaser.getCompany());
                params.put("licenseNumber",purchaser.getLicenseNumber());
                params.put("enterpriseType",purchaser.getEnterpriseType());
                params.put("employeesNum",purchaser.getEmployeesNum());
                params.put("mainIndustryId",purchaser.getMainIndustryId());
                params.put("productStyleId",purchaser.getProductStyleId());
                params.put("mainProducts",purchaser.getMainProducts());
                params.put("mainCustomerGroups",purchaser.getMainCustomerGroups());
                params.put("yearSales",purchaser.getYearSales());
                params.put("quId",purchaser.getQuId());
                params.put("detailAddress",purchaser.getDetailAddress());
                params.put("businessContacts",purchaser.getBusinessContacts());
                params.put("contactPhone",purchaser.getContactPhone());
                params.put("mainSalesChannels",purchaser.getMainSalesChannels());
                params.put("monthSales",purchaser.getMonthSales());
                break;
            case SUPPLIER:
                params.put("type","supplier");
                params.put("id",supplier.getId());
                params.put("company",supplier.getCompany());
                params.put("licenseNumber",supplier.getLicenseNumber());
                params.put("enterpriseType",supplier.getEnterpriseType());
                params.put("employeesNum",supplier.getEmployeesNum());
                params.put("mainIndustryId",supplier.getMainIndustryId());
                params.put("productStyleId",supplier.getProductStyleId());
                params.put("mainProducts",supplier.getMainProducts());
                params.put("mainCustomerGroups",supplier.getMainCustomerGroups());
                params.put("yearSales",supplier.getYearSales());
                params.put("quId",supplier.getQuId());
                params.put("detailAddress",supplier.getDetailAddress());
                params.put("businessContacts",supplier.getBusinessContacts());
                params.put("contactPhone",supplier.getContactPhone());
                params.put("shopIntroduction",supplier.getShopIntroduction());
                params.put("monthProduction",supplier.getMonthProduction());
                params.put("yearExportNum",supplier.getYearExportNum());
                break;
        }
        post(context, Server.getUrl("valueAdd/applyRole"),params,listener,new ApplyRoleParser(listener));
    }


    /**
     * 获得采购商/供应商申请费用
     * @param context
     * @param accessToken
     * @param type
     * @param listener
     */
    public void applyRoleFee(Context context, String accessToken, ValueAddActivity.VALUE_ADD_TYPE type,ApplyRoleFeeListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        switch (type){
            case PURCHASER:
                params.put("type","purchaser");
                break;
            case SUPPLIER:
                params.put("type","supplier");
                break;
        }
        post(context, Server.getUrl("valueAdd/applyRoleFee"),params,listener,new ApplyRoleFeeParser(listener));
    }
}
