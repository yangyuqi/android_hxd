package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qiuweiyu on 2017/8/31.
 */

public class SendCodeParser extends AbsBaseParser {
    public SendCodeParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
            SendCodeListener listener = (SendCodeListener) mOnBaseRequestListener.get();
            if (listener!=null){
                listener.getCode();
            }
    }
}
