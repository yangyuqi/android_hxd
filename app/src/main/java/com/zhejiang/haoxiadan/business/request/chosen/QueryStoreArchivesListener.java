package com.zhejiang.haoxiadan.business.request.chosen;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Shop;
import com.zhejiang.haoxiadan.model.common.Supplier;

/**
 * 获取商家档案
 * Created by KK on 2017/9/7.
 */
public interface QueryStoreArchivesListener extends OnBaseRequestListener {

    void onQueryStoreArchivesSuccess(Supplier supplier);

}
