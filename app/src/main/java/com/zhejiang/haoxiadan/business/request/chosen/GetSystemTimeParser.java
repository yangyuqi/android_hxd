package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * Created by qiuweiyu on 2017/11/2.
 */

public class GetSystemTimeParser extends AbsBaseParser {
    public GetSystemTimeParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        GetSystemTimeListener listener = (GetSystemTimeListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.ongetTime(data);
        }
    }
}
