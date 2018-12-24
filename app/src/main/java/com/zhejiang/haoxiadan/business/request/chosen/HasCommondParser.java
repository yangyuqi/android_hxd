package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.choseModel.CommondGoodsBean;

/**
 * Created by qiuweiyu on 2017/12/25.
 */

public class HasCommondParser extends AbsBaseParser{
    public HasCommondParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        CommondGoodsBean goodsBean = mDataParser.parseObject(data,CommondGoodsBean.class);
        HasCommondLiatener liatener = (HasCommondLiatener) mOnBaseRequestListener.get();
        if (liatener!=null){
            liatener.onGetData(goodsBean);
        }
    }
}
