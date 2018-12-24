package com.zhejiang.haoxiadan.business.request.chat;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.chat.Customer;

/**
 * Created by Administrator on 2017/9/6 0006.
 */

public interface QueryCustomerByStoreIdListener extends OnBaseRequestListener {
    void onQueryCustomerSuccess(Customer customer);
}
