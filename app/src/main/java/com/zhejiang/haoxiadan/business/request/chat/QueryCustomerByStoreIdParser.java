package com.zhejiang.haoxiadan.business.request.chat;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.business.request.my.LoginListener;
import com.zhejiang.haoxiadan.model.requestData.out.chat.Customer;

/**
 * Created by wqd on 2017/9/6 0006.
 */

public class QueryCustomerByStoreIdParser extends AbsBaseParser {

    public QueryCustomerByStoreIdParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }
    @Override
    protected void parseData(@Nullable String data) {
        Customer customer = mDataParser.parseObject(data,Customer.class);
        QueryCustomerByStoreIdListener queryCustomerByStoreIdListener = (QueryCustomerByStoreIdListener) mOnBaseRequestListener.get();
        if (queryCustomerByStoreIdListener!=null){
            queryCustomerByStoreIdListener.onQueryCustomerSuccess(customer);
        }
    }
}
