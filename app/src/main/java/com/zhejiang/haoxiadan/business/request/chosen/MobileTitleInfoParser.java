package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.TitleInfoChannelFloor;

/**
 * Created by qiuweiyu on 2017/9/12.
 */

public class MobileTitleInfoParser extends AbsBaseParser {
    public MobileTitleInfoParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        TitleInfoChannelFloor channelFloor = mDataParser.parseObject(data,TitleInfoChannelFloor.class);
        MobileTitleInfoListener listener = (MobileTitleInfoListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.getChannel(channelFloor.getChannel());
        }
    }
}
