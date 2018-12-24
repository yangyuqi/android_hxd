package com.zhejiang.haoxiadan.business.request.cart;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * 确认收货
 * Created by KK on 2017/9/6.
 */
public class OrderSureReceiveParser extends AbsBaseParser {

    public OrderSureReceiveParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        OrderSureReceiveListener listener = (OrderSureReceiveListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onOrderSureReceiveSuccess();
        }
    }

}
