package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.TopInfoList;

/**
 * Created by qiuweiyu on 2017/9/8.
 */

public class TopInfoParser extends AbsBaseParser {
    public TopInfoParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        TopInfoList list = mDataParser.parseObject(data,TopInfoList.class);
        TopInfoListener listener = (TopInfoListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.getTopInfo(list);
        }
    }
}
