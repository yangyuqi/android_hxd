package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.PrefenceFactoryData;

/**
 * Created by qiuweiyu on 2017/9/15.
 */

public class PrefenceFactoryParser extends AbsBaseParser {
    public PrefenceFactoryParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        PrefenceFactoryData factoryData = mDataParser.parseObject(data,PrefenceFactoryData.class);
        PrefenceFactoryListener listener = (PrefenceFactoryListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.getData(factoryData);
        }
    }
}
