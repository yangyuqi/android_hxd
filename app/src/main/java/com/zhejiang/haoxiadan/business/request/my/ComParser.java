package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * Created by qiuweiyu on 2017/8/31.
 */

public class ComParser extends AbsBaseParser {
    public ComParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
            ComListener listener = (ComListener) mOnBaseRequestListener.get();
            if (listener != null) {
                listener.onResponse();
            }
    }
}
