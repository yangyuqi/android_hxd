package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * Created by qiuweiyu on 2017/9/8.
 */

public class EditUnickNameParser extends AbsBaseParser {
    public EditUnickNameParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        EditUnickNameListener listener = (EditUnickNameListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.onSuccess();
        }
    }
}
