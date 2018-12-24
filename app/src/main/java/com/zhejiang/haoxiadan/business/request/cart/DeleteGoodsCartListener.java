package com.zhejiang.haoxiadan.business.request.cart;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Cart;

import java.util.List;

/**
 * 删除购物车商品
 * Created by KK on 2017/9/1.
 */
public interface DeleteGoodsCartListener extends OnBaseRequestListener {

    void onDeleteGoodsCartSuccess();

}
