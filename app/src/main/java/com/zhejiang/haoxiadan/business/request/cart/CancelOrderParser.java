package com.zhejiang.haoxiadan.business.request.cart;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * 确认收货
 * Created by KK on 2017/9/6.
 */
public class CancelOrderParser extends AbsBaseParser {

    public CancelOrderParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        CancelOrderListener listener = (CancelOrderListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onCancelOrderSuccess();
        }
    }

}
