package com.zhejiang.haoxiadan.business.request.cart;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Logistics;

/**
 * 提醒卖家发货
 * Created by KK on 2017/9/6.
 */
public interface RemindDeliverListener extends OnBaseRequestListener {

    void onRemindDeliverSuccess();

}
