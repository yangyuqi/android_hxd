package com.zhejiang.haoxiadan.model.requestData.out;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/2.
 */

public class TradeListData {
    private int tradeId ;
    private String tradeName ;
    private List<TradeListStyleBean> styles ;

    public int getId() {
        return tradeId;
    }

    public void setId(int id) {
        this.tradeId = id;
    }

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public List<TradeListStyleBean> getStyles() {
        return styles;
    }

    public void setStyles(List<TradeListStyleBean> styles) {
        this.styles = styles;
    }

    public TradeListData(int id, String tradeName, List<TradeListStyleBean> styles) {

        this.tradeId = id;
        this.tradeName = tradeName;
        this.styles = styles;
    }
}
