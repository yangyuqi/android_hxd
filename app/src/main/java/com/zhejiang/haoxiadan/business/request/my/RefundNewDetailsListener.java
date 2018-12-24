package com.zhejiang.haoxiadan.business.request.my;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsGspData2;
import com.zhejiang.haoxiadan.model.requestData.out.my.RefundNewBean;
import com.zhejiang.haoxiadan.model.requestData.out.my.RefundNewBeanDetail;

import java.util.List;

public interface RefundNewDetailsListener extends OnBaseRequestListener {
    void onRefundOrderFormStatusByIdSuccess(RefundNewBeanDetail refundNewBean);
}
