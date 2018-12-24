package com.zhejiang.haoxiadan.business.request.hot;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * Created by qiuweiyu on 2017/11/9.
 */

public class WelcomePhotoParser extends AbsBaseParser {
    public WelcomePhotoParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        WelcomePhotoListener listener = (WelcomePhotoListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.onGetPhoto(data);
        }
    }
}
