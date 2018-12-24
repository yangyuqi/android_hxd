package com.zhejiang.haoxiadan.business.request.my;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.CollectGoodsBeanData;

/**
 * Created by qiuweiyu on 2017/9/1.
 */

public interface CollectionGoodsListener extends OnBaseRequestListener {
    void coollectData(CollectGoodsBeanData data);
}
