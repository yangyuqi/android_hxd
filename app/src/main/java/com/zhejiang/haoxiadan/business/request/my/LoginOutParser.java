package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * Created by qiuweiyu on 2017/8/31.
 */

public class LoginOutParser extends AbsBaseParser{
    public LoginOutParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        LoginOutListener listener = (LoginOutListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.loginOut();
        }
    }
}
