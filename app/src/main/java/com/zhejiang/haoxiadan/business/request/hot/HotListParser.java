package com.zhejiang.haoxiadan.business.request.hot;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.hot.HotspotTypeList;

/**
 * Created by qiuweiyu on 2017/9/4.
 */

public class HotListParser extends AbsBaseParser {
    public HotListParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        HotspotTypeList list = mDataParser.parseObject(data,HotspotTypeList.class);
        HotListListener listener = (HotListListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.onGetData(list);
        }
    }
}
