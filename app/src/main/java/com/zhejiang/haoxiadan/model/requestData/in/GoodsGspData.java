package com.zhejiang.haoxiadan.model.requestData.in;

/**
 * Created by KK on 2017/9/1.
 */
public class GoodsGspData {
    private long goodsCartId;//
    private String cartGsp;//
    private int count;//
    private double price;//
    private double nowPrice;//
    private String specColor;//
    private String specSize;//
    private Object goodsSpecName;
    private Object goodsSpecContent;

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

    public long getGoodsCartId() {
        return goodsCartId;
    }

    public void setGoodsCartId(long goodsCartId) {
        this.goodsCartId = goodsCartId;
    }

    public String getCartGsp() {
        return cartGsp;
    }

    public void setCartGsp(String cartGsp) {
        this.cartGsp = cartGsp;
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
}
