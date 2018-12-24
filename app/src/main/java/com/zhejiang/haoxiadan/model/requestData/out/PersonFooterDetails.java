package com.zhejiang.haoxiadan.model.requestData.out;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qiuweiyu on 2017/8/31.
 */

public class PersonFooterDetails implements Serializable{
    private String mobileDate ;
    private List<GoodsInfoBean> ftList ;
    private int count ;

    public String getData() {
        return mobileDate;
    }

    public void setData(String data) {
        this.mobileDate = data;
    }

    public List<GoodsInfoBean> getFtList() {
        return ftList;
    }

    public void setFtList(List<GoodsInfoBean> ftList) {
        this.ftList = ftList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
