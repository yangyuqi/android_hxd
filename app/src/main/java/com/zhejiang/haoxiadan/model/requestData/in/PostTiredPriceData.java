package com.zhejiang.haoxiadan.model.requestData.in;

import com.zhejiang.haoxiadan.model.requestData.out.TiredPriceData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by qiuweiyu on 2017/9/4.
 */

public class PostTiredPriceData implements Serializable{
    public ArrayList<TiredPriceData> getTired_data_list() {
        return tired_data_list;
    }

    public void setTired_data_list(ArrayList<TiredPriceData> tired_data_list) {
        this.tired_data_list = tired_data_list;
    }

    private ArrayList<TiredPriceData> tired_data_list ;
}
