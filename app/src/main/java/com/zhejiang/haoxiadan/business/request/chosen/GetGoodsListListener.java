package com.zhejiang.haoxiadan.business.request.chosen;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.choseModel.GoodsListData;

/**
 * Created by qiuweiyu on 2017/9/5.
 */

public interface GetGoodsListListener extends OnBaseRequestListener {
    void getGoodsList(GoodsListData listData);
}
