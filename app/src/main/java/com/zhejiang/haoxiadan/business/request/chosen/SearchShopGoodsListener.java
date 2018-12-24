package com.zhejiang.haoxiadan.business.request.chosen;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.choseModel.SearchData;
import com.zhejiang.haoxiadan.model.common.Goods;

import java.util.List;

/**
 * 店铺内搜索商品
 * Created by KK on 2017/9/13.
 */

public interface SearchShopGoodsListener extends OnBaseRequestListener {
    void onSearchShopGoodsSuccess(List<Goods> goodsList);
}
