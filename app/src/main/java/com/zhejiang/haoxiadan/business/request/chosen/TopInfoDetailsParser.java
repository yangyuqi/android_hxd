package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.TopInfoData;

/**
 * Created by qiuweiyu on 2017/9/8.
 */

public class TopInfoDetailsParser extends AbsBaseParser {
    public TopInfoDetailsParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        TopInfoData infoData = mDataParser.parseObject(data,TopInfoData.class);
        TopInfoDetailsListener listener = (TopInfoDetailsListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.getData(infoData);
        }
    }
}
