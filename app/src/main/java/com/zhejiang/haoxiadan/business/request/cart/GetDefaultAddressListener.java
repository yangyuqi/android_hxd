package com.zhejiang.haoxiadan.business.request.cart;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Address;

import java.util.List;
import java.util.Map;

/**
 * 获取默认地址
 * Created by KK on 2017/9/15.
 */
public interface GetDefaultAddressListener extends OnBaseRequestListener {

    void onGetDefaultAddressSuccess(Address address);

}
