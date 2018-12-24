package com.zhejiang.haoxiadan.business.request.cart;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * 删除订单
 * Created by KK on 2017/9/6.
 */
public class DeleteOrderFormParser extends AbsBaseParser {

    public DeleteOrderFormParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        DeleteOrderFormListener listener = (DeleteOrderFormListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onDeleteOrderFormSuccess();
        }
    }

}
