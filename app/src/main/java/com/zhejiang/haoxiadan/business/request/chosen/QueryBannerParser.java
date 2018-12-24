package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.BannersData;

/**
 * Created by qiuweiyu on 2017/9/12.
 */

public class QueryBannerParser extends AbsBaseParser {
    public QueryBannerParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        BannersData bannersData = mDataParser.parseObject(data,BannersData.class);
        QueryBannerListener listener = (QueryBannerListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.getList(bannersData);
        }
    }
}
