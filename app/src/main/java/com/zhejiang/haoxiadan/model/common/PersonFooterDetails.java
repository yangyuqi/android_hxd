package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by qiuweiyu on 2017/8/31.
 */

public class PersonFooterDetails implements Serializable{
    private String date ;
    private List<GoodsInfoBean> ftList ;
    private int count ;

    public String getData() {
        return date;
    }

    public void setData(String data) {
        this.date = data;
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
