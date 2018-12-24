package com.zhejiang.haoxiadan.business.request.hot;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.hot.HotHotData;

/**
 * Created by qiuweiyu on 2017/9/7.
 */

public class HotDetailsDataParser extends AbsBaseParser {
    public HotDetailsDataParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        HotHotData hotHotData = mDataParser.parseObject(data,HotHotData.class);
        HotDetailsDataListener listener = (HotDetailsDataListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.getString(hotHotData);
        }
    }
}
