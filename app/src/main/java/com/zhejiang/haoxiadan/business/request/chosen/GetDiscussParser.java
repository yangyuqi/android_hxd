package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.DiscussContentData;

/**
 * Created by qiuweiyu on 2017/9/12.
 */

public class GetDiscussParser extends AbsBaseParser {
    public GetDiscussParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        DiscussContentData contentData = mDataParser.parseObject(data,DiscussContentData.class);
        GetDiscussListener listener = (GetDiscussListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.getDiscuss(contentData);
        }

    }
}
