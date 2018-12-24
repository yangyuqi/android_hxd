package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.choseModel.SearchData;

/**
 * Created by qiuweiyu on 2017/9/6.
 */

public class SearchGoodsParser extends AbsBaseParser {
    public SearchGoodsParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        SearchData searchData = mDataParser.parseObject(data,SearchData.class);
        SearchGoodsListener listener = (SearchGoodsListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.getData(searchData);
        }
    }
}
