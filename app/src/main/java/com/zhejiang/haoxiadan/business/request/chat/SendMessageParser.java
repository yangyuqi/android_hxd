package com.zhejiang.haoxiadan.business.request.chat;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.chat.Customer;

/**
 * Created by wqd on 2017/9/6 0006.
 */

public class SendMessageParser extends AbsBaseParser {

    public SendMessageParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }
    @Override
    protected void parseData(@Nullable String data) {
        SendMessageListener sendMessageListener = (SendMessageListener) mOnBaseRequestListener.get();
        if (sendMessageListener!=null){
            sendMessageListener.onSendMessageSuccess();
        }
    }
}
