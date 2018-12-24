package com.zhejiang.haoxiadan.business.request.cart;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Address;
import com.zhejiang.haoxiadan.model.common.CartGoods;

import java.util.List;

/**
 * 查看修改购物车结算状态后数据列表
 * Created by KK on 2017/9/1.
 */
public interface SelectBalanceAllListener extends OnBaseRequestListener {

    void onSelectBalanceAllSuccess(Address address, List<CartGoods> cartGoodsList);

}
