package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.PersonFooter;

/**
 * Created by qiuweiyu on 2017/8/31.
 */

public class PersonFooterParser extends AbsBaseParser {
    public PersonFooterParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        PersonFooter footer = mDataParser.parseObject(data,PersonFooter.class);
        PersonFootListener listener = (PersonFootListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.getFooter(footer);
        }
    }
}
