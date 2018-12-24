package com.zhejiang.haoxiadan.business.request.cart;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * 购物车状态修改为2
 * Created by KK on 2017/9/1.
 */
public class BalanceStatusParser extends AbsBaseParser {

    public BalanceStatusParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        BalanceStatusListener listener = (BalanceStatusListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onBalanceStatusSuccess();
        }
    }

}
