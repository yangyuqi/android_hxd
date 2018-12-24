package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by KK on 2017/9/1.
 */
public class SelectBalanceAllData {

    private List<GoodsListsData> goodsVoList;//	购物车列表相关信息
    private String nowDate;//	系统当前日期
    private ReceiverAddrVoData receiverAddrVo;//	用户收货地址信息

    public List<GoodsListsData> getGoodsVoList() {
        return goodsVoList;
    }

    public void setGoodsVoList(List<GoodsListsData> goodsVoList) {
        this.goodsVoList = goodsVoList;
    }

    public String getNowDate() {
        return nowDate;
    }

    public void setNowDate(String nowDate) {
        this.nowDate = nowDate;
    }

    public ReceiverAddrVoData getReceiverAddrVo() {
        return receiverAddrVo;
    }

    public void setReceiverAddrVo(ReceiverAddrVoData receiverAddrVo) {
        this.receiverAddrVo = receiverAddrVo;
    }
}
