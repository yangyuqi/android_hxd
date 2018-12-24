package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;
import java.util.List;

/**
 * 商品的规格
 * Created by KK on 2017/8/23.
 */
public class GoodsStyle implements Serializable {
    //商品图标
    private String icon;
    //商品名称
    private String title;
    //单价
    private double price;

    private String id;
    //主规格
    private String style;
    //从规格
    private List<GoodsStyleSecond> secondStyles;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public List<GoodsStyleSecond> getSecondStyles() {
        return secondStyles;
    }

    public void setSecondStyles(List<GoodsStyleSecond> secondStyles) {
        this.secondStyles = secondStyles;
    }
}
