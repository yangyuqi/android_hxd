package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;
import android.util.Log;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.ChoseGoodsFloorData;

/**
 * Created by qiuweiyu on 2017/9/2.
 */

public class FloorParser extends AbsBaseParser {
    public FloorParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        ChoseGoodsFloorData floorData = mDataParser.parseObject(data,ChoseGoodsFloorData.class);
        FloorListener listener = (FloorListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.getHomeData(floorData);
        }
    }
}
