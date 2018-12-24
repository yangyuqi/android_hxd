package com.zhejiang.haoxiadan.business.request.cart;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Address;
import com.zhejiang.haoxiadan.model.common.CartGoods;
import com.zhejiang.haoxiadan.model.common.Order;

import java.util.List;

/**
 * 根据用户id获取所有订单信息
 * Created by KK on 2017/9/5.
 */
public interface SelectOrderFormAllListener extends OnBaseRequestListener {

    void onSelectOrderFormAllSuccess(List<Order> orders);

}
