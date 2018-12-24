package com.zhejiang.haoxiadan.business.request.hot;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.hot.HotspotInfoListBean;

/**
 * Created by qiuweiyu on 2017/9/7.
 */

public class HotDetailsParser extends AbsBaseParser {
    public HotDetailsParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        HotspotInfoListBean listBean = mDataParser.parseObject(data,HotspotInfoListBean.class);
        HotDetailsListener listener = (HotDetailsListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.onGetData(listBean);
        }
    }
}
