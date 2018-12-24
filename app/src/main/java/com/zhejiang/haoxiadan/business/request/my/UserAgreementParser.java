package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qiuweiyu on 2017/9/19.
 */

public class UserAgreementParser extends AbsBaseParser {
    public UserAgreementParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        try {
            JSONObject object = new JSONObject(data);
            UserAgreementListener listener = (UserAgreementListener) mOnBaseRequestListener.get();
            if (listener!=null){
                listener.onArticleSuccess(object.getString("content"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
