package com.zhejiang.haoxiadan.model.requestData.in;

import com.zhejiang.haoxiadan.model.requestData.out.GetGoodsPhotoData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by qiuweiyu on 2017/9/1.
 */

public class PostGoodsPhotoBean implements Serializable{
    private ArrayList<String> normal_lsit ;
    private int positopn ;

    public int getPositopn() {
        return positopn;
    }

    public void setPositopn(int positopn) {
        this.positopn = positopn;
    }

    public ArrayList<String> getNormal_lsit() {
        return normal_lsit;
    }

    public void setNormal_lsit(ArrayList<String> normal_lsit) {
        this.normal_lsit = normal_lsit;
    }
}
