package com.zhejiang.haoxiadan.business.request.cart;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.ConsultHistory;
import com.zhejiang.haoxiadan.model.common.RefundDetail;

import java.util.List;

/**
 * 根据订单id，查询退款状态
 * Created by KK on 2017/9/7.
 */
public interface RefundDetailListener extends OnBaseRequestListener {

    void onRefundOrderFormStatusByIdSuccess(RefundDetail refundDetail,List<ConsultHistory> consultHistories);

}
