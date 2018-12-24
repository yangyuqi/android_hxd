package com.zhejiang.haoxiadan.business.request.cart;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * 确认收货
 * Created by KK on 2017/9/6.
 */
public interface OrderSureReceiveListener extends OnBaseRequestListener {

    void onOrderSureReceiveSuccess();

}
