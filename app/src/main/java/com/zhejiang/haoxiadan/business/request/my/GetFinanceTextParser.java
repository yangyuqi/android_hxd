package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.my.FinanceTextData;

/**
 * Created by qiuweiyu on 2017/9/22.
 */

public class GetFinanceTextParser extends AbsBaseParser{
    public GetFinanceTextParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        FinanceTextData textData = mDataParser.parseObject(data,FinanceTextData.class);
        GetFinanceTextListener listener = (GetFinanceTextListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.getData(textData);
        }
    }
}
