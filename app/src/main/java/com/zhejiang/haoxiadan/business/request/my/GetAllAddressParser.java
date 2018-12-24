package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.my.CurrentUserAddressBean;
import com.zhejiang.haoxiadan.model.requestData.out.my.CurrentUserAddressData;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/4.
 */

public class GetAllAddressParser extends AbsBaseParser {
    public GetAllAddressParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        CurrentUserAddressData userAddressBeen = mDataParser.parseObject(data,CurrentUserAddressData.class);
        GetAllAddressListener listener = (GetAllAddressListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.getUserAddress(userAddressBeen.getAddressList());
        }
    }
}
