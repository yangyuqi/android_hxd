package com.zhejiang.haoxiadan.business.request.cart;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * 取消订单
 * Created by KK on 2017/9/6.
 */
public interface CancelOrderListener extends OnBaseRequestListener {

    void onCancelOrderSuccess();

}
