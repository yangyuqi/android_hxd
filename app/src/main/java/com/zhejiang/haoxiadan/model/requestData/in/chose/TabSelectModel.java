package com.zhejiang.haoxiadan.model.requestData.in.chose;

/**
 * Created by qiuweiyu on 2017/10/24.
 */

public class TabSelectModel {
    private int index ;
    private String type ;

    public TabSelectModel(){

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TabSelectModel(int index, String type) {

        this.index = index;
        this.type = type;
    }
}
