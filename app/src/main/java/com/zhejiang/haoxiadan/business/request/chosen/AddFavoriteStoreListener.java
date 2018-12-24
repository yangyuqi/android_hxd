package com.zhejiang.haoxiadan.business.request.chosen;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Supplier;

/**
 * 新增收藏夹店铺信息,再次点击则取消收藏
 * Created by KK on 2017/9/8.
 */
public interface AddFavoriteStoreListener extends OnBaseRequestListener {

    void onAddFavoriteStoreSuccess();

}
