package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;
import android.util.Log;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.UserInfo;

/**
 * Created by qiuweiyu on 2017/8/31.
 */

public class GetUserInfoParser extends AbsBaseParser {
    public GetUserInfoParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        UserInfo userInfo = mDataParser.parseObject(data,UserInfo.class);
        GetUserListener listener = (GetUserListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.getUserInfo(userInfo);
        }
    }
}
