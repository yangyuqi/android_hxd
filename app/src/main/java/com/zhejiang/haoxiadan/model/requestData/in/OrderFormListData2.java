package com.zhejiang.haoxiadan.model.requestData.in;

import com.zhejiang.haoxiadan.model.requestData.RefundApplyFormListData2;

import java.util.List;

/**
 * Created by KK on 2017/9/6.
 */
public class OrderFormListData2 {
    private String id;//	订单id	是	String
    private String orderId;//	订单号	是	String
    private String addtime;//	下单时间	是	String
    private String paymentName;//	付款方式名称	是	String
    private String receiverName;//
    private long receiverAddrId;//	地址id	是	Long
    private String receiverArea;//	区域	是	String
    private String receiverAreaInfo;//	详细地址	是	String
    private String receiverMobile;//	手机号码	是	String
    private String receiverTelephone;//	联系电话	是	String
    private String receiverZip;//	邮政编码	是	String
    private String goodsInfo;//	商品信息	是	Json
    private List<GoodsInfoListData> goodsInfoList;//	商品信息对象
    private double totalPrice;//	订单总价	是	BigDecimal
    private double goodsAmount;//	订单商品总价		BigDecimal
    private double shipPrice;//	邮费	是	Int
    private String orderStatus;//	订单状态	是	String
    private String shipCode;//	订单的物流编号 是	String
    private String payTime;//	订单付款时间（成交时间）	是	Strng
    private String shipTime;//	发货时间
    private String confirmTime;//	确认收货时间
    private String finishTime;//	完成时间
    private String msg;//	买家留言
    private String payType;//	支付类型	是	String
    private String currentTimeStamp;//	查询订单的当前时间戳	是	String
    private String storeId;//	店铺id
    private String storeName;//	店铺名称
    private int refundStatus;//		是
    private long orderformMainId;//
    private int isAlready;// 是否已经完成该众筹规则(1-已完成，0未完成）
    private double revisedPrice;//如果订单已修改价格，则此字段为订单的总价格
    private int isChangePrice;//如果为1表示订单已修改价格
    private List<RefundApplyFormListData2> refundApplyFormList;//此订单的退款记录
    private String orderExplain ;

    public String getOrderExplain() {
        return orderExplain;
    }

    public void setOrderExplain(String orderExplain) {
        this.orderExplain = orderExplain;
    }

    public List<RefundApplyFormListData2> getRefundApplyFormList() {
        return refundApplyFormList;
    }

    public void setRefundApplyFormList(List<RefundApplyFormListData2> refundApplyFormList) {
        this.refundApplyFormList = refundApplyFormList;
    }

    public int getIsAlready() {
        return isAlready;
    }

    public void setIsAlready(int isAlready) {
        this.isAlready = isAlready;
    }

    public double getRevisedPrice() {
        return revisedPrice;
    }

    public void setRevisedPrice(double revisedPrice) {
        this.revisedPrice = revisedPrice;
    }

    public int getIsChangePrice() {
        return isChangePrice;
    }

    public void setIsChangePrice(int isChangePrice) {
        this.isChangePrice = isChangePrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public long getReceiverAddrId() {
        return receiverAddrId;
    }

    public void setReceiverAddrId(long receiverAddrId) {
        this.receiverAddrId = receiverAddrId;
    }

    public String getReceiverArea() {
        return receiverArea;
    }

    public void setReceiverArea(String receiverArea) {
        this.receiverArea = receiverArea;
    }

    public String getReceiverAreaInfo() {
        return receiverAreaInfo;
    }

    public void setReceiverAreaInfo(String receiverAreaInfo) {
        this.receiverAreaInfo = receiverAreaInfo;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverTelephone() {
        return receiverTelephone;
    }

    public void setReceiverTelephone(String receiverTelephone) {
        this.receiverTelephone = receiverTelephone;
    }

    public String getReceiverZip() {
        return receiverZip;
    }

    public void setReceiverZip(String receiverZip) {
        this.receiverZip = receiverZip;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public List<GoodsInfoListData> getGoodsInfoList() {
        return goodsInfoList;
    }

    public void setGoodsInfoList(List<GoodsInfoListData> goodsInfoList) {
        this.goodsInfoList = goodsInfoList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(double goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public double getShipPrice() {
        return shipPrice;
    }

    public void setShipPrice(double shipPrice) {
        this.shipPrice = shipPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getShipCode() {
        return shipCode;
    }

    public void setShipCode(String shipCode) {
        this.shipCode = shipCode;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getShipTime() {
        return shipTime;
    }

    public void setShipTime(String shipTime) {
        this.shipTime = shipTime;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCurrentTimeStamp() {
        return currentTimeStamp;
    }

    public void setCurrentTimeStamp(String currentTimeStamp) {
        this.currentTimeStamp = currentTimeStamp;
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

    public int getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(int refundStatus) {
        this.refundStatus = refundStatus;
    }

    public long getOrderformMainId() {
        return orderformMainId;
    }

    public void setOrderformMainId(long orderformMainId) {
        this.orderformMainId = orderformMainId;
    }
}
