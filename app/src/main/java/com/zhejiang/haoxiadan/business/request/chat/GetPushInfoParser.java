package com.zhejiang.haoxiadan.business.request.chat;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.PushInfo;
import com.zhejiang.haoxiadan.model.requestData.out.chat.ChatUser;

import java.util.List;

/**
 * Created by wqd on 2017/9/7 0006.
 */

public class GetPushInfoParser extends AbsBaseParser {

    public GetPushInfoParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }
    @Override
    protected void parseData(@Nullable String data) {
        List<PushInfo> pushList = mDataParser.parseList(data,PushInfo.class);
        GetPushInfoListener getPushInfoListener = (GetPushInfoListener) mOnBaseRequestListener.get();
        if (getPushInfoListener!=null){
            getPushInfoListener.onGetPushInfoSuccess(pushList);
        }
    }
}
