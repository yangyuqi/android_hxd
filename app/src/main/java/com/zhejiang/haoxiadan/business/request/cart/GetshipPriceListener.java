package com.zhejiang.haoxiadan.business.request.cart;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Address;
import com.zhejiang.haoxiadan.model.common.CartGoods;

import java.util.List;
import java.util.Map;

/**
 * 获取邮费接口
 * Created by KK on 2017/9/5.
 */
public interface GetshipPriceListener extends OnBaseRequestListener {

    void onGetshipPriceSuccess(List<Map<String,String>> singleList,double shipPrice);

}
