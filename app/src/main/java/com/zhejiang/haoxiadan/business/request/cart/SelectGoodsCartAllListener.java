package com.zhejiang.haoxiadan.business.request.cart;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Cart;

import java.util.List;

/**
 * 根据用户id获取购物车商品信息
 * Created by KK on 2017/8/23.
 */
public interface SelectGoodsCartAllListener  extends OnBaseRequestListener {

    void onSelectGoodsCartAllSuccess(List<Cart> cartList);

}
