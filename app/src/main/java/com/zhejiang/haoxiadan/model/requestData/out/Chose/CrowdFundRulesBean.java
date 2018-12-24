package com.zhejiang.haoxiadan.model.requestData.out.Chose;

import com.zhejiang.haoxiadan.model.requestData.out.GetGoodsIntData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/13.
 */

public class CrowdFundRulesBean implements Serializable{
    private int isAlready ;
    private int goodsId ;
    private int checkOutCount ;
    private int crowdfundingCount ;
    private int orderCount ;
    private List<GetGoodsIntData> goodsGsp ;

    public List<GetGoodsIntData> getGoodsGsp() {
        return goodsGsp;
    }

    public void setGoodsGsp(List<GetGoodsIntData> goodsGsp) {
        this.goodsGsp = goodsGsp;
    }

    public int getIsAlready() {
        return isAlready;
    }

    public void setIsAlready(int isAlready) {
        this.isAlready = isAlready;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getCheckOutCount() {
        return checkOutCount;
    }

    public void setCheckOutCount(int checkOutCount) {
        this.checkOutCount = checkOutCount;
    }

    public int getCrowdfundingCount() {
        return crowdfundingCount;
    }

    public void setCrowdfundingCount(int crowdfundingCount) {
        this.crowdfundingCount = crowdfundingCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }


}
