package com.zhejiang.haoxiadan.business.request.pay;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * 获得订单中金银行卡支付code
 * Created by KK on 2017/9/9.
 */
public class GetValueAddPayCodeParser extends AbsBaseParser {

    public GetValueAddPayCodeParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {

        GetValueAddPayCodeListener listener = (GetValueAddPayCodeListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onGetValueAddPayCodeSuccess();
        }
    }

}
