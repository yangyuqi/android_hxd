package com.zhejiang.haoxiadan.business.request.chat;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.OrderMessage;
import com.zhejiang.haoxiadan.model.common.PushInfo;

import java.util.List;

/**
 * Created by wqd on 2017/9/7 0006.
 */

public interface OrderFormAllForMessageListener extends OnBaseRequestListener {
    void onGetPushInfoSuccess(List<OrderMessage> pushList);
}
