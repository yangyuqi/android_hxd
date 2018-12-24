package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * Created by qiuweiyu on 2017/9/13.
 */

public class DeleteAllFooterParser extends AbsBaseParser {
    public DeleteAllFooterParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        DeleteAllFooterListener listener = (DeleteAllFooterListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.onSuccess();
        }
    }
}
