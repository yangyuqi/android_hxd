package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Shop;
import com.zhejiang.haoxiadan.model.common.Supplier;
import com.zhejiang.haoxiadan.model.requestData.in.QueryStoreArchivesData;
import com.zhejiang.haoxiadan.model.requestData.in.QueryStoreByIdData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取商家档案
 * Created by KK on 2017/9/7.
 */
public class QueryStoreArchivesParser extends AbsBaseParser {
    public QueryStoreArchivesParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {

        JSONObject storeArchives = null;
        try {
            JSONObject jsonObject = new JSONObject(data);
            storeArchives = jsonObject.getJSONObject("StoreArchives");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        QueryStoreArchivesData queryStoreArchivesData = mDataParser.parseObject(storeArchives.toString(),QueryStoreArchivesData.class);
        Supplier supplier = mapped(queryStoreArchivesData);

        QueryStoreArchivesListener listener = (QueryStoreArchivesListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onQueryStoreArchivesSuccess(supplier);
        }
    }

    private Supplier mapped(QueryStoreArchivesData queryStoreArchivesData){
        Supplier supplier = new Supplier();
        supplier.setCompany(queryStoreArchivesData.getCompany());
        supplier.setLicenseNumber(queryStoreArchivesData.getLicenseNumber());
        supplier.setEnterpriseType(queryStoreArchivesData.getEnterpriseType());
        supplier.setEmployeesNum(queryStoreArchivesData.getEmployeesNum());
        supplier.setShopIntroduction(queryStoreArchivesData.getStore_info());
        supplier.setMainIndustry(queryStoreArchivesData.getMainIndustry());
        supplier.setProductStyle(queryStoreArchivesData.getProductStyle());
        supplier.setMainProducts(queryStoreArchivesData.getMainProducts());
        supplier.setMainCustomerGroups(queryStoreArchivesData.getMainCustomerGroups());
        supplier.setMonthProduction(queryStoreArchivesData.getMonthProduction());
        supplier.setYearSales(queryStoreArchivesData.getYearSales());
        supplier.setYearExportNum(queryStoreArchivesData.getYearExportNum());

        return supplier;
    }
}
