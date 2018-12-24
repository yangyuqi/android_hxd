package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.my.CollectStoreData;

/**
 * Created by qiuweiyu on 2017/9/1.
 */

public class CollectionStoreParser extends AbsBaseParser {
    public CollectionStoreParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        CollectStoreData storeData = mDataParser.parseObject(data,CollectStoreData.class);
        CollectStoreListener listener = (CollectStoreListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.getData(storeData);
        }
    }
}
