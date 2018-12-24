package com.zhejiang.haoxiadan.business.request.my;

import com.zhejiang.haoxiadan.model.requestData.out.TradeListData;

import java.util.List;

/**
 * Created by KK on 2017/9/4.
 */
public class QueryTradeInfoData {
    private List<TradeListData> TradeList;
    private List<TradeListData> mainBuyTradeList;
    private List<TradeListData> followTradeList;

    public List<TradeListData> getTradeList() {
        return TradeList;
    }

    public void setTradeList(List<TradeListData> tradeList) {
        this.TradeList = tradeList;
    }

    public List<TradeListData> getMainBuyTradeList() {
        return mainBuyTradeList;
    }

    public void setMainBuyTradeList(List<TradeListData> mainBuyTradeList) {
        this.mainBuyTradeList = mainBuyTradeList;
    }

    public List<TradeListData> getFollowTradeList() {
        return followTradeList;
    }

    public void setFollowTradeList(List<TradeListData> followTradeList) {
        this.followTradeList = followTradeList;
    }
}
