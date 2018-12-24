package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;
import java.util.List;

/**
 * 主营行业
 * Created by KK on 2017/8/23.
 */
public class Industry implements Serializable {
    private String id;
    private String title;
    //行业下的产品风格
    private List<ProductStyle> productStyles;

    private boolean isChoose;

    private boolean disable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ProductStyle> getProductStyles() {
        return productStyles;
    }

    public void setProductStyles(List<ProductStyle> productStyles) {
        this.productStyles = productStyles;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    @Override
    public String toString() {
        return title;
    }
}
