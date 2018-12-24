package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * Created by qiuweiyu on 2017/9/20.
 */

public class LoanApplyParser extends AbsBaseParser {
    public LoanApplyParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        LoanApplyListener loanApplyListener = (LoanApplyListener) mOnBaseRequestListener.get();
        if (loanApplyListener!=null){
            loanApplyListener.onSucccess();
        }
    }
}
