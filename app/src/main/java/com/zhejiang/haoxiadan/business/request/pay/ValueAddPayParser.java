package com.zhejiang.haoxiadan.business.request.pay;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * 增值服务中金银行卡支付
 * Created by KK on 2017/9/9.
 */
public class ValueAddPayParser extends AbsBaseParser {

    public ValueAddPayParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        ValueAddPayListener listener = (ValueAddPayListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onValueAddPaySuccess();
        }
    }

}
