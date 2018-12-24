package com.zhejiang.haoxiadan.business.request.cart;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Logistics;

/**
 * 提醒卖家发货
 * Created by KK on 2017/9/6.
 */
public class RemindDeliverParser extends AbsBaseParser {

    public RemindDeliverParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        RemindDeliverListener listener = (RemindDeliverListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onRemindDeliverSuccess();
        }
    }

}
