package com.zhejiang.haoxiadan.business.request.chosen;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.TradeListDataList;

/**
 * Created by qiuweiyu on 2017/9/2.
 */

public interface GetTradeInfoListener extends OnBaseRequestListener{
    void getTrade(TradeListDataList list);
}
