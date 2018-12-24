package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.GetDetailsData;

/**
 * Created by qiuweiyu on 2017/9/1.
 */

public class GoodsDetailsParser extends AbsBaseParser {
    public GoodsDetailsParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        GetDetailsData detailsData = mDataParser.parseObject(data,GetDetailsData.class);
        GoodsDetailsListener listener = (GoodsDetailsListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.getGoods(detailsData);
        }
    }
}
