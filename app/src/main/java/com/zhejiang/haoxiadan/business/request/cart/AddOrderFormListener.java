package com.zhejiang.haoxiadan.business.request.cart;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Order;

import java.util.List;

/**
 * 新增订单
 * Created by KK on 2017/9/5.
 */
public interface AddOrderFormListener extends OnBaseRequestListener {

    void onAddOrderFormSuccess(String orderId);

}
