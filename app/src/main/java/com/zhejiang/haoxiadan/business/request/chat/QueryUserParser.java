package com.zhejiang.haoxiadan.business.request.chat;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.chat.ChatUser;

/**
 * Created by wqd on 2017/9/6 0006.
 */

public class QueryUserParser extends AbsBaseParser {

    public QueryUserParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }
    @Override
    protected void parseData(@Nullable String data) {
        ChatUser chatUser = mDataParser.parseObject(data,ChatUser.class);
        QueryUserListener queryUserListener = (QueryUserListener) mOnBaseRequestListener.get();
        if (queryUserListener!=null){
            queryUserListener.onQueryUserSuccess(chatUser);
        }
    }
}
