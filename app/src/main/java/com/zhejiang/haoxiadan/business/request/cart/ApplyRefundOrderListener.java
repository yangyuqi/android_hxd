package com.zhejiang.haoxiadan.business.request.cart;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * 申请退款
 * Created by KK on 2017/9/7.
 */
public interface ApplyRefundOrderListener extends OnBaseRequestListener {

    void onApplyRefundOrderSuccess();

}
