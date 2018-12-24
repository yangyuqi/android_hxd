package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;
import java.util.List;

/**
 * 商品
 * Created by KK on 2017/8/23.
 */
public class Goods implements Serializable {
    //
    private String id;
    //图标
    private String icon;
    //标题
    private String title;
    //单价
    private double price;
    //月销售量
    private int monthSale;
    //发货地
    private String deliveryPlace;
    //商品可选规格
    private List<GoodsStyle> goodsStyles;
    //火拼结束时间(毫秒)
    private long togetherEndMillis;
    //火拼规则
    private List<TogetherRule> togetherRules;
    //评价
    private List<Evaluate> evaluates;
    //供应商
    private Supplier supplier;
    //是否收藏
    private boolean isCollect;

    //图文介绍
    private String introduce;
    //规格参数
    private String parameter;

    //货号
    private String goodsNo;

    private int goodsNumType ;

    public int getGoodsNumType() {
        return goodsNumType;
    }

    public void setGoodsNumType(int goodsNumType) {
        this.goodsNumType = goodsNumType;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMonthSale() {
        return monthSale;
    }

    public void setMonthSale(int monthSale) {
        this.monthSale = monthSale;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public List<GoodsStyle> getGoodsStyles() {
        return goodsStyles;
    }

    public void setGoodsStyles(List<GoodsStyle> goodsStyles) {
        this.goodsStyles = goodsStyles;
    }

    public long getTogetherEndMillis() {
        return togetherEndMillis;
    }

    public void setTogetherEndMillis(long togetherEndMillis) {
        this.togetherEndMillis = togetherEndMillis;
    }

    public List<TogetherRule> getTogetherRules() {
        return togetherRules;
    }

    public void setTogetherRules(List<TogetherRule> togetherRules) {
        this.togetherRules = togetherRules;
    }

    public List<Evaluate> getEvaluates() {
        return evaluates;
    }

    public void setEvaluates(List<Evaluate> evaluates) {
        this.evaluates = evaluates;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
