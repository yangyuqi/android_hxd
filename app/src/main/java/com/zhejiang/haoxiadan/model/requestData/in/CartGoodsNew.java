package com.zhejiang.haoxiadan.model.requestData.in;

import com.zhejiang.haoxiadan.model.common.CartGoodsStyle;
import com.zhejiang.haoxiadan.model.common.LevelPrice;

import java.util.List;

/**
 * Created by qiuweiyu on 2018/1/11.
 */

public class CartGoodsNew {
    private String id;
    //商品图标
    private String icon;
    //商品标题
    private String title;
    //火拼结束时间(毫秒)
    private long togetherEndMillis;
    //商品规格
    private List<CartGoodsStyle> goodsStyles;
    //阶梯价格
    private List<LevelPrice> levelPrices;

    //商品类型（0：普通 1：拼单 ）
    private String goodsType;

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public long getTogetherEndMillis() {
        return togetherEndMillis;
    }

    public void setTogetherEndMillis(long togetherEndMillis) {
        this.togetherEndMillis = togetherEndMillis;
    }

    public List<CartGoodsStyle> getGoodsStyles() {
        return goodsStyles;
    }

    public void setGoodsStyles(List<CartGoodsStyle> goodsStyles) {
        this.goodsStyles = goodsStyles;
    }

    public List<LevelPrice> getLevelPrices() {
        return levelPrices;
    }

    public void setLevelPrices(List<LevelPrice> levelPrices) {
        this.levelPrices = levelPrices;
    }
}
