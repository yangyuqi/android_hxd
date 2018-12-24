package com.zhejiang.haoxiadan.business.request.my;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Purchaser;
import com.zhejiang.haoxiadan.model.common.Supplier;
import com.zhejiang.haoxiadan.ui.activity.my.ValueAddActivity;

/**
 * 采购商/供应商申请信息
 * Created by KK on 2017/9/4.
 */
public interface ApplyRoleInfoListener extends OnBaseRequestListener {

    void onApplyRoleInfoSuccess(ValueAddActivity.VALUE_ADD_STATUS status,ValueAddActivity.VALUE_ADD_TYPE type, String validityDate, Purchaser purchaser,Supplier supplier);

}
