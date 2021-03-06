package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * Created by qiuweiyu on 2017/9/4.
 */

public class AddToCartParser extends AbsBaseParser {
    public AddToCartParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        AddToCartListener listener = (AddToCartListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.success();
        }
    }
}
