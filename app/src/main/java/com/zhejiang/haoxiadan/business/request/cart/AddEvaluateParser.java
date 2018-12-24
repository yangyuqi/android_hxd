package com.zhejiang.haoxiadan.business.request.cart;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * 新增订单评论
 * Created by KK on 2017/9/8.
 */
public class AddEvaluateParser extends AbsBaseParser {

    public AddEvaluateParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        AddEvaluateListener listener = (AddEvaluateListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onAddEvaluateSuccess();
        }
    }

}
