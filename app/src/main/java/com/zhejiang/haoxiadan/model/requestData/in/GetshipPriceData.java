package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by KK on 2017/9/5.
 */
public class GetshipPriceData {
    private String shipPrice;
    private List<SinglePriceListData> singlePriceList;

    public String getShipPrice() {
        return shipPrice;
    }

    public void setShipPrice(String shipPrice) {
        this.shipPrice = shipPrice;
    }

    public List<SinglePriceListData> getSinglePriceList() {
        return singlePriceList;
    }

    public void setSinglePriceList(List<SinglePriceListData> singlePriceList) {
        this.singlePriceList = singlePriceList;
    }
}
