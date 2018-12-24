package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.CollectGoodsBeanData;

/**
 * Created by qiuweiyu on 2017/9/1.
 */

public class CollectionGoodsParser extends AbsBaseParser {
    public CollectionGoodsParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        CollectGoodsBeanData  beanData = mDataParser.parseObject(data,CollectGoodsBeanData.class);
        CollectionGoodsListener listener = (CollectionGoodsListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.coollectData(beanData);
        }
    }
}
