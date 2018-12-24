package com.zhejiang.haoxiadan.business.request.cart;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * 延时收货
 * Created by KK on 2017/9/6.
 */
public interface OrderConfirmDelayListener extends OnBaseRequestListener {

    void onOrderConfirmDelaySuccess();

}
