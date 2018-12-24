package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by KK on 2017/9/5.
 */
public class SelectOrderFormAllData {

    private List<OrderFormListData> orderFormList;

    public List<OrderFormListData> getOrderFormList() {
        return orderFormList;
    }

    public void setOrderFormList(List<OrderFormListData> orderFormList) {
        this.orderFormList = orderFormList;
    }
}
