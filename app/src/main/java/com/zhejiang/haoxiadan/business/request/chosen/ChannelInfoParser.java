package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.ChoseGoodsFloorData;

/**
 * Created by qiuweiyu on 2017/10/11.
 */

public class ChannelInfoParser extends AbsBaseParser {
    public ChannelInfoParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        ChoseGoodsFloorData floorData = mDataParser.parseObject(data,ChoseGoodsFloorData.class);
        ChannelInfoListener infoListener = (ChannelInfoListener) mOnBaseRequestListener.get();
        if (infoListener!=null){
            infoListener.getFloor(floorData);
        }
    }
}
