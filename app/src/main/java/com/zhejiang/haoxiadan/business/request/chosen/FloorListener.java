package com.zhejiang.haoxiadan.business.request.chosen;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.ChoseGoodsFloorData;

/**
 * Created by qiuweiyu on 2017/9/2.
 */

public interface FloorListener extends OnBaseRequestListener {

    void getHomeData(ChoseGoodsFloorData homeData);

}
