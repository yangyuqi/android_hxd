package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

public class CloudPayParser extends AbsBaseParser {
    public CloudPayParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        CloudPayListener listener = (CloudPayListener) mOnBaseRequestListener.get();
        if (listener != null) {
            listener.onSuccsee(data);
        }
    }
}
