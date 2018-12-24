package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;
import java.util.List;

/**
 * 退款详情
 * Created by KK on 2017/8/23.
 */
public class RefundDetail implements Serializable {
    private String id;
    //退款状态
    private REFUND_STATUS refundStatus;
    //商品列表
    private List<OrderGoods> goodsList;
    //供应商
    private Supplier supplier;
    //退款原因
    private String reason;
    //退款说明
    private String explain;
    //退款金额
    private String price;
    //图片
    private List<String> imgs;
    //申请时间
    private String applyTime;
    //退款编号
    private String refundNo;

    public enum REFUND_STATUS{
        //退款中
        REFUND_ING,
        //退款完成
        REFUND_COMPLETE,
        //退款失败
        REFUND_FAILED
    }

    public REFUND_STATUS getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(REFUND_STATUS refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<OrderGoods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<OrderGoods> goodsList) {
        this.goodsList = goodsList;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getRefundNo() {
        return refundNo;
    }

    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }
}
