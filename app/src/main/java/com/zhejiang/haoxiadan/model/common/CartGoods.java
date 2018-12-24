package com.zhejiang.haoxiadan.model.common;

import com.zhejiang.haoxiadan.model.requestData.in.CartGoodsNew;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车的商品
 * Created by KK on 2017/8/23.
 */
public class CartGoods implements Serializable {
    private String id;
    //商品类型（0：普通 1：拼单 ）
    private String goodsType;
    //商品图标
    private String icon;
    //商品标题
    private String title;
    //火拼结束时间(毫秒)
    private long togetherEndMillis;
    //商品规格
    private List<CartGoodsStyle> goodsStyles;
    //运费
    private double shipPrice;
    //留言
    private String message;
    //店铺id
    private String storeId;
    //阶梯价格
    private List<LevelPrice> levelPrices;
    //货号
    private String goodsNo;
    //最低购买量
    private int goodsLimit;

    //是否选择
    private boolean isChoose;

    //商品价格
    private double goodsPrice ;

    private String msg ;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public List<CartGoodsNew> getCartGoodsList() {
        return cartGoodsList;
    }

    public void setCartGoodsList(List<CartGoodsNew> cartGoodsList) {
        this.cartGoodsList = cartGoodsList;
    }

    private List<CartGoodsNew> cartGoodsList ;

    public int getGoodsLimit() {
        return goodsLimit;
    }

    public void setGoodsLimit(int goodsLimit) {
        this.goodsLimit = goodsLimit;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public List<LevelPrice> getLevelPrices() {
        return levelPrices;
    }

    public void setLevelPrices(List<LevelPrice> levelPrices) {
        this.levelPrices = levelPrices;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
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

    public double getShipPrice() {
        return shipPrice;
    }

    public void setShipPrice(double shipPrice) {
        this.shipPrice = shipPrice;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
