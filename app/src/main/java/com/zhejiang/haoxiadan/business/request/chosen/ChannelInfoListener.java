package com.zhejiang.haoxiadan.business.request.chosen;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.ChoseGoodsFloorData;

/**
 * Created by qiuweiyu on 2017/10/11.
 */

public interface ChannelInfoListener extends OnBaseRequestListener{
    void getFloor(ChoseGoodsFloorData floorData);
}
