package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * Created by qiuweiyu on 2017/9/20.
 */

public class GoRegisterFinanceUserParser extends AbsBaseParser {
    public GoRegisterFinanceUserParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        GoRegisterFinanceUserListener listener = (GoRegisterFinanceUserListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.onSuccess();
        }
    }
}
