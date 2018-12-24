package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.TradeListData;
import com.zhejiang.haoxiadan.model.requestData.out.TradeListDataList;

/**
 * Created by qiuweiyu on 2017/9/2.
 */

public class GetTradeInfoParser extends AbsBaseParser {
    public GetTradeInfoParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        TradeListDataList list = mDataParser.parseObject(data, TradeListDataList.class);
        GetTradeInfoListener listener = (GetTradeInfoListener) mOnBaseRequestListener.get();
        if (listener!= null){
            listener.getTrade(list);
        }
    }
}
