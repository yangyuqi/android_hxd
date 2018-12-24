package com.zhejiang.haoxiadan.model.requestData.out;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/1.
 */

public class GetDetailsData {
    private GetGoodsBean goods ;
    private List<lmjaData> lmja ;
    private long currentTime ;
    private String haoCommand ;

    public String getHaoCommand() {
        return haoCommand;
    }

    public void setHaoCommand(String haoCommand) {
        this.haoCommand = haoCommand;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public GetGoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GetGoodsBean goods) {
        this.goods = goods;
    }

    public List<lmjaData> getLmja() {
        return lmja;
    }

    public void setLmja(List<lmjaData> lmja) {
        this.lmja = lmja;
    }
}
