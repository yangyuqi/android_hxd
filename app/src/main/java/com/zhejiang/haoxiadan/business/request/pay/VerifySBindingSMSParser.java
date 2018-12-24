package com.zhejiang.haoxiadan.business.request.pay;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 验证绑定短信验证码
 * Created by KK on 2017/9/8.
 */
public class VerifySBindingSMSParser extends AbsBaseParser {

    public VerifySBindingSMSParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        VerifySBindingSMSListener listener = (VerifySBindingSMSListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onVerifySBindingSMSSuccess();
        }
    }

}
