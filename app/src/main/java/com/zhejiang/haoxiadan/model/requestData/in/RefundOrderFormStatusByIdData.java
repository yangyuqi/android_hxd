package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by KK on 2017/9/7.
 */
public class RefundOrderFormStatusByIdData {
    private List<RefundApplyFormListData> refundApplyFormList;

    public List<RefundApplyFormListData> getRefundApplyFormList() {
        return refundApplyFormList;
    }

    public void setRefundApplyFormList(List<RefundApplyFormListData> refundApplyFormList) {
        this.refundApplyFormList = refundApplyFormList;
    }
}
