package com.zhejiang.haoxiadan.business.request.chosen;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.GetDetailsData;
import com.zhejiang.haoxiadan.model.requestData.out.GetGoodsIntData;

/**
 * Created by qiuweiyu on 2017/9/1.
 */

public interface GoodsDetailsListener extends OnBaseRequestListener {
    void getGoods(GetDetailsData getDetailsData);
}
