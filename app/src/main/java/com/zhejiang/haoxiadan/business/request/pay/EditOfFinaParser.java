package com.zhejiang.haoxiadan.business.request.pay;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * Created by qiuweiyu on 2018/6/27.
 */

public class EditOfFinaParser extends AbsBaseParser {
    public EditOfFinaParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        EditOfFinaListener listener = (EditOfFinaListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.onSuccess();
        }
    }
}
