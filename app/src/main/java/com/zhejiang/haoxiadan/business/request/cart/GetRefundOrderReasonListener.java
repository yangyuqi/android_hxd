package com.zhejiang.haoxiadan.business.request.cart;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Reason;

import java.util.List;

/**
 * 退款原因
 * Created by KK on 2017/9/6.
 */
public interface GetRefundOrderReasonListener extends OnBaseRequestListener {

    void onGetRefundOrderReasonSuccess(List<Reason> reasons);

}
