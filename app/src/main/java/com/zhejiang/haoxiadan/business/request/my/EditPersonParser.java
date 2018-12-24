package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * Created by qiuweiyu on 2017/8/31.
 */

public class EditPersonParser extends AbsBaseParser {
    public EditPersonParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        EditPersonInfoListener infoListener = (EditPersonInfoListener) mOnBaseRequestListener.get();
        if (infoListener!=null){
            infoListener.onSuccess();
        }
    }
}
