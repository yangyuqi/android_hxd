package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Industry;
import com.zhejiang.haoxiadan.model.common.ProductStyle;
import com.zhejiang.haoxiadan.model.requestData.out.TradeListData;
import com.zhejiang.haoxiadan.model.requestData.out.TradeListStyleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户关注行业信息新增
 * Created by KK on 2017/11/24.
 */
public class InsertUserTradeInfoParser extends AbsBaseParser {

    public InsertUserTradeInfoParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {

        InsertUserTradeInfoListener listener = (InsertUserTradeInfoListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onInsertUserTradeInfoSuccess();
        }
    }
}
