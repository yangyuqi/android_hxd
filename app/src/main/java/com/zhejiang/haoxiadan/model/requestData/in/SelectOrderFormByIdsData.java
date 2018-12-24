package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by KK on 2017/9/6.
 */
public class SelectOrderFormByIdsData {
    private long currentTime;//
    private int totalAllCount;//
    private List<OrderFormListData2> orderFormList;//
    private String PriceAddshipPrice;//
    private String GoodsAmountAll;//
    private String totalAllshipPrice;//
    private long orderformMainId;//
    private String mainOrder_id;

    public String getMainOrder_id() {
        return mainOrder_id;
    }

    public void setMainOrder_id(String mainOrder_id) {
        this.mainOrder_id = mainOrder_id;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public int getTotalAllCount() {
        return totalAllCount;
    }

    public void setTotalAllCount(int totalAllCount) {
        this.totalAllCount = totalAllCount;
    }

    public List<OrderFormListData2> getOrderFormList() {
        return orderFormList;
    }

    public void setOrderFormList(List<OrderFormListData2> orderFormList) {
        this.orderFormList = orderFormList;
    }

    public String getPriceAddshipPrice() {
        return PriceAddshipPrice;
    }

    public void setPriceAddshipPrice(String priceAddshipPrice) {
        PriceAddshipPrice = priceAddshipPrice;
    }

    public String getGoodsAmountAll() {
        return GoodsAmountAll;
    }

    public void setGoodsAmountAll(String goodsAmountAll) {
        GoodsAmountAll = goodsAmountAll;
    }

    public String getTotalAllshipPrice() {
        return totalAllshipPrice;
    }

    public void setTotalAllshipPrice(String totalAllshipPrice) {
        this.totalAllshipPrice = totalAllshipPrice;
    }

    public long getOrderformMainId() {
        return orderformMainId;
    }

    public void setOrderformMainId(long orderformMainId) {
        this.orderformMainId = orderformMainId;
    }
}
