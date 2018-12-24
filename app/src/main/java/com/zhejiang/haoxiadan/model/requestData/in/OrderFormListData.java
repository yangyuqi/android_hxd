package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by KK on 2017/9/5.
 */
public class OrderFormListData {

    private int id;//	订单id	是	String
    private String order_id;//	订单号	是	String
    private String addtime;//	下单时间	是	String
    private String payment_name;//	付款方式名称	是	String
    private long receiver_addr_id;//	地址id	是	Long
    private String receiver_area;//	区域	是	String
    private String receiver_area_info;//	详细地址	是	String
    private String receiver_mobile;//	手机号码	是	String
    private String receiver_telephone;//	联系电话	是	String
    private String receiver_zip;//	邮政编码	是	String
    private String goodsInfo;//	商品信息	是	Json
    private List<GoodsInfoListData> goodsInfoList;
    private double totalPrice;//	订单总价	是	BigDecimal
    private double goods_amount;//	订单商品总价		BigDecimal
    private String shipPrice;//	邮费	是	Int
    private String orderStatus;//	订单状态	是	String
    private String shipCode;//	订单的物流编号 是	String
    private String payTime;//	订单付款时间（成交时间）	是	Strng
    private String payType;//	支付类型	是	String
    private String currentTimeStamp;//	查询订单的当前时间戳	是	String
    private String storeId;//	店铺id
    private String storeName;//	店铺名称
    private int refundStatus;//		是
    private int isAlready;// 是否已经完成该众筹规则(1-已完成，0未完成）
    private double revisedPrice;//如果订单已修改价格，则此字段为订单的总价格
    private int isChangePrice;//如果为1表示订单已修改价格
    private List<RefundApplyFormListData> refundApplyFormList;//此订单的退款记录

    private String orderId ;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    private String orderExplain ;

    public String getOrderExplain() {
        return orderExplain;
    }

    public void setOrderExplain(String orderExplain) {
        this.orderExplain = orderExplain;
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

    public List<RefundApplyFormListData> getRefundApplyFormList() {
        return refundApplyFormList;
    }

    public void setRefundApplyFormList(List<RefundApplyFormListData> refundApplyFormList) {
        this.refundApplyFormList = refundApplyFormList;
    }

    public List<GoodsInfoListData> getGoodsInfoList() {
        return goodsInfoList;
    }

    public void setGoodsInfoList(List<GoodsInfoListData> goodsInfoList) {
        this.goodsInfoList = goodsInfoList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getPayment_name() {
        return payment_name;
    }

    public void setPayment_name(String payment_name) {
        this.payment_name = payment_name;
    }

    public long getReceiver_addr_id() {
        return receiver_addr_id;
    }

    public void setReceiver_addr_id(long receiver_addr_id) {
        this.receiver_addr_id = receiver_addr_id;
    }

    public String getReceiver_area() {
        return receiver_area;
    }

    public void setReceiver_area(String receiver_area) {
        this.receiver_area = receiver_area;
    }

    public String getReceiver_area_info() {
        return receiver_area_info;
    }

    public void setReceiver_area_info(String receiver_area_info) {
        this.receiver_area_info = receiver_area_info;
    }

    public String getReceiver_mobile() {
        return receiver_mobile;
    }

    public void setReceiver_mobile(String receiver_mobile) {
        this.receiver_mobile = receiver_mobile;
    }

    public String getReceiver_telephone() {
        return receiver_telephone;
    }

    public void setReceiver_telephone(String receiver_telephone) {
        this.receiver_telephone = receiver_telephone;
    }

    public String getReceiver_zip() {
        return receiver_zip;
    }

    public void setReceiver_zip(String receiver_zip) {
        this.receiver_zip = receiver_zip;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getGoods_amount() {
        return goods_amount;
    }

    public void setGoods_amount(double goods_amount) {
        this.goods_amount = goods_amount;
    }

    public String getShipPrice() {
        return shipPrice;
    }

    public void setShipPrice(String shipPrice) {
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
}
