package com.zhejiang.haoxiadan.business.request.cart;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Order;

/**
 * 根据订单id，订单详情
 * Created by KK on 2017/9/5.
 */
public interface SelectOrderFormByIdsListener extends OnBaseRequestListener {

    void onSelectOrderFormByIdsSuccess(Order order);

}
