package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;
import android.util.Log;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.in.AreaInfoBean;

/**
 * Created by qiuweiyu on 2017/9/5.
 */

public class InitAddressParser extends AbsBaseParser {
    public InitAddressParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
//        AreaInfoBean bean = mDataParser.parseObject(data,AreaInfoBean.class);
        InitAddressListener listener = (InitAddressListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.getAddress(data);
        }
    }
}
