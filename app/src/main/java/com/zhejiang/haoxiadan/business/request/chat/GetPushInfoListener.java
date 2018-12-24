package com.zhejiang.haoxiadan.business.request.chat;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.PushInfo;
import com.zhejiang.haoxiadan.model.requestData.out.chat.ChatUser;

import java.util.List;

/**
 * Created by wqd on 2017/9/7 0006.
 */

public interface GetPushInfoListener extends OnBaseRequestListener {
    void onGetPushInfoSuccess(List<PushInfo> pushList);
}
