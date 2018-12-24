package com.zhejiang.haoxiadan.model.requestData.in;

/**
 * Created by KK on 2017/9/1.
 */
public class GoodsGspListData {
    private long goodsCartId;//	购物车id	是	String
    private String goodsSpecName;//	规格名稱數組	是	String
    private String goodsSpecNontent;//	规格名稱對應的内容	是	String
    private String cartGsp;//	商品规格id	是	String
    private int count;//	商品数量	是
    private double price;//	对应商品的总价	是	String
    private double nowPrice;//	商品当前单价	是	String
    private String specInfo;//	商品规格字符串		String
    private int specpropertySmallCount;//	最低购买量	是	Int
    private int specpropertyInventCount;//最高购买量
    private String specColor;//		是	String	颜色:黑色
    private String specSize;//		是	String	尺码:M

    public String getSpecColor() {
        return specColor;
    }

    public void setSpecColor(String specColor) {
        this.specColor = specColor;
    }

    public String getSpecSize() {
        return specSize;
    }

    public void setSpecSize(String specSize) {
        this.specSize = specSize;
    }

    public String getGoodsSpecName() {
        return goodsSpecName;
    }

    public void setGoodsSpecName(String goodsSpecName) {
        this.goodsSpecName = goodsSpecName;
    }

    public String getGoodsSpecNontent() {
        return goodsSpecNontent;
    }

    public void setGoodsSpecNontent(String goodsSpecNontent) {
        this.goodsSpecNontent = goodsSpecNontent;
    }

    public String getCartGsp() {
        return cartGsp;
    }

    public void setCartGsp(String cartGsp) {
        this.cartGsp = cartGsp;
    }

    public String getSpecInfo() {
        return specInfo;
    }

    public void setSpecInfo(String specInfo) {
        this.specInfo = specInfo;
    }

    public int getSpecpropertySmallCount() {
        return specpropertySmallCount;
    }

    public void setSpecpropertySmallCount(int specpropertySmallCount) {
        this.specpropertySmallCount = specpropertySmallCount;
    }

    public int getSpecpropertyInventCount() {
        return specpropertyInventCount;
    }

    public void setSpecpropertyInventCount(int specpropertyInventCount) {
        this.specpropertyInventCount = specpropertyInventCount;
    }

    public long getGoodsCartId() {
        return goodsCartId;
    }

    public void setGoodsCartId(long goodsCartId) {
        this.goodsCartId = goodsCartId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(double nowPrice) {
        this.nowPrice = nowPrice;
    }
}
