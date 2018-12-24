package com.zhejiang.haoxiadan.model.requestData.out;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/2.
 */

public class TradeListDataList {
    public List<TradeListData> getTradeList() {
        return TradeList;
    }

    public void setTradeList(List<TradeListData> tradeList) {
        TradeList = tradeList;
    }

    private List<TradeListData> TradeList ;
}
