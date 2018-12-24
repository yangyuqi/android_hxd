package com.zhejiang.haoxiadan.model.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单
 * Created by KK on 2017/8/23.
 */
public class Order {
    private String id;
    private String mainId;
    private String mainOrderNo;
    private long orderformMainId ;

    public long getOrderformMainId() {
        return orderformMainId;
    }

    public void setOrderformMainId(long orderformMainId) {
        this.orderformMainId = orderformMainId;
    }

    //订单状态
    private ORDER_STATUS orderStatus;
    //火拼结束时间(毫秒)
    private long togetherEndMillis;
    //地址
    private Address address;
    //买家留言
    private String message;

    //商品列表
    private ArrayList<OrderGoods> goodsList;

    //商品总数
    private int goodsCount;
    //商品总价
    private double totalGoodsPrice;
    //运费
    private double shipPrice;
    //总价
    private double totalPrice;
    //供应商
    private Supplier supplier;
    //订单编号
    private String orderNo;
    //运单号
    private String shipNo;
    //下单时间
    private String addTime;
    //支付时间
    private String payTime;
    //支付类型
    private String payType;
    //发货时间
    private String shipTime;
    //是否能退款
    private boolean canRefund;

    private String refundReason;

    private String orderExplain ;

    public String getOrderExplain() {
        return orderExplain;
    }

    public void setOrderExplain(String orderExplain) {
        this.orderExplain = orderExplain;
    }

    public enum ORDER_STATUS{
        ALL,
        //售后管理
        AFTER_SALES,
        //待付款
        WAIT_PAY,
        //待发货
        WAIT_SHIP,
        //待收货
        WAIT_RECEIVE,
        //待评价
        WAIT_EVALUATE,
        //已完成
        COMPLETE,
        //已取消
        CANCEL,
        //已申请退款
        APPLY_REFUND,
        //退款中
        REFUND_ING,
        //退款完成
        REFUND_COMPLETE,
//        //时间结束
//        TIME_END,
        //未成团
        END,
        //商家一次不同意
        SALER_REFUSE_ONE,
        //商家二次不同意
        SALER_REFUSE_TWO
    }

    public String getMainOrderNo() {
        return mainOrderNo;
    }

    public void setMainOrderNo(String mainOrderNo) {
        this.mainOrderNo = mainOrderNo;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String getShipNo() {
        return shipNo;
    }

    public void setShipNo(String shipNo) {
        this.shipNo = shipNo;
    }

    public boolean isCanRefund() {
        return canRefund;
    }

    public void setCanRefund(boolean canRefund) {
        this.canRefund = canRefund;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ORDER_STATUS getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(ORDER_STATUS orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getShipTime() {
        return shipTime;
    }

    public void setShipTime(String shipTime) {
        this.shipTime = shipTime;
    }

    public long getTogetherEndMillis() {
        return togetherEndMillis;
    }

    public void setTogetherEndMillis(long togetherEndMillis) {
        this.togetherEndMillis = togetherEndMillis;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<OrderGoods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(ArrayList<OrderGoods> goodsList) {
        this.goodsList = goodsList;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public double getTotalGoodsPrice() {
        return totalGoodsPrice;
    }

    public void setTotalGoodsPrice(double totalGoodsPrice) {
        this.totalGoodsPrice = totalGoodsPrice;
    }

    public double getShipPrice() {
        return shipPrice;
    }

    public void setShipPrice(double shipPrice) {
        this.shipPrice = shipPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
