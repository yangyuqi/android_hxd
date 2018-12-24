package com.zhejiang.haoxiadan.model.requestData.out;

import com.zhejiang.haoxiadan.model.choseModel.HotCategoryListData;

/**
 * Created by qiuweiyu on 2017/9/2.
 */

public class HotCategoryBean {
    private String storeId ;
    private String storeName ;

    private String decorationType ;
    private String img ;
    private String value ;
    private String type ;
    private String titleName ;

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getDecorationType() {
        return decorationType;
    }

    public void setDecorationType(String decorationType) {
        this.decorationType = decorationType;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
