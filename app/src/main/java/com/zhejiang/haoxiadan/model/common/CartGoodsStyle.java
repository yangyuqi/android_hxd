package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车商品的规格
 * Created by KK on 2017/8/24.
 */
public class CartGoodsStyle implements Serializable {
    private String id;
    //购物车Id
    private String cartId;
    //是否选择
    private boolean isChoose;
    //购买数量
    private int buyNum;
    //单价
    private double price;
    //主规格
    private String mainStyleName;
    private String mainStyle;
    //从规格
    private String secondStyleName;
    private String secondStyle;
    //最低购买量
    private int minBuyCount;
    //最高购买量
    private int maxBuyCount;
    private GOODS_TYPE type = GOODS_TYPE.STOCK;

    private String goodsGsp;
    private Object goodsSpecName;
    private Object goodsSpecContent;

    public enum GOODS_TYPE{
        STOCK,//现货
        FUTURES//期货
    }

    public GOODS_TYPE getType() {
        return type;
    }

    public void setType(GOODS_TYPE type) {
        this.type = type;
    }

    public Object getGoodsSpecName() {
        return goodsSpecName;
    }

    public void setGoodsSpecName(Object goodsSpecName) {
        this.goodsSpecName = goodsSpecName;
    }

    public Object getGoodsSpecContent() {
        return goodsSpecContent;
    }

    public void setGoodsSpecContent(Object goodsSpecContent) {
        this.goodsSpecContent = goodsSpecContent;
    }

    public String getGoodsGsp() {
        return goodsGsp;
    }

    public void setGoodsGsp(String goodsGsp) {
        this.goodsGsp = goodsGsp;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMinBuyCount() {
        return minBuyCount;
    }

    public void setMinBuyCount(int minBuyCount) {
        this.minBuyCount = minBuyCount;
    }

    public int getMaxBuyCount() {
        return maxBuyCount;
    }

    public void setMaxBuyCount(int maxBuyCount) {
        this.maxBuyCount = maxBuyCount;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMainStyleName() {
        return mainStyleName;
    }

    public void setMainStyleName(String mainStyleName) {
        this.mainStyleName = mainStyleName;
    }

    public String getSecondStyleName() {
        return secondStyleName;
    }

    public void setSecondStyleName(String secondStyleName) {
        this.secondStyleName = secondStyleName;
    }

    public String getMainStyle() {
        return mainStyle;
    }

    public void setMainStyle(String mainStyle) {
        this.mainStyle = mainStyle;
    }

    public String getSecondStyle() {
        return secondStyle;
    }

    public void setSecondStyle(String secondStyle) {
        this.secondStyle = secondStyle;
    }
}
