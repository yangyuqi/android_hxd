package com.zhejiang.haoxiadan.business.request.cart;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Logistics;
import com.zhejiang.haoxiadan.model.common.Order;

/**
 * 订单物流信息接口
 * Created by KK on 2017/9/6.
 */
public interface GetExpressListener extends OnBaseRequestListener {

    void onGetExpressSuccess(Logistics logistics);

}
