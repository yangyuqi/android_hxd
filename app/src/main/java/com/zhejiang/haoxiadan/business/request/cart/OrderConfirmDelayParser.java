package com.zhejiang.haoxiadan.business.request.cart;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * 延时收货
 * Created by KK on 2017/9/6.
 */
public class OrderConfirmDelayParser extends AbsBaseParser {

    public OrderConfirmDelayParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        OrderConfirmDelayListener listener = (OrderConfirmDelayListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onOrderConfirmDelaySuccess();
        }
    }

}
