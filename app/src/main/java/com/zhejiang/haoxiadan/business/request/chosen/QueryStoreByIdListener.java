package com.zhejiang.haoxiadan.business.request.chosen;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Purchaser;
import com.zhejiang.haoxiadan.model.common.Shop;
import com.zhejiang.haoxiadan.model.common.Supplier;
import com.zhejiang.haoxiadan.ui.activity.my.ValueAddActivity;

/**
 * 获取商家信息
 * Created by KK on 2017/9/7.
 */
public interface QueryStoreByIdListener extends OnBaseRequestListener {

    void onQueryStoreByIdSuccess(Shop shop);

}
