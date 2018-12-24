package com.zhejiang.haoxiadan.model.requestData.out;

import java.io.Serializable;

/**
 * Created by qiuweiyu on 2017/9/1.
 */

public class TiredPriceData implements Serializable{
    private String price ;
    private String count ;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
