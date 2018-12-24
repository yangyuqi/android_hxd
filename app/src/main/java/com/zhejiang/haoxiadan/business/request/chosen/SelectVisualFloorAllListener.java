package com.zhejiang.haoxiadan.business.request.chosen;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.ForwardItem;
import com.zhejiang.haoxiadan.model.common.Goods;
import com.zhejiang.haoxiadan.model.common.ShopFloor;
import com.zhejiang.haoxiadan.model.common.ShopTab;

import java.util.List;

/**
 * 店铺首页楼层
 * Created by KK on 2017/9/7.
 */
public interface SelectVisualFloorAllListener extends OnBaseRequestListener {

    void onSelectVisualFloorAllSuccess(List<ShopFloor> floors);

}
