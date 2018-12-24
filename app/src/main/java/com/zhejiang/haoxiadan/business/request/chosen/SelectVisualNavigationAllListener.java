package com.zhejiang.haoxiadan.business.request.chosen;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.ShopTab;
import com.zhejiang.haoxiadan.model.common.Supplier;

import java.util.List;

/**
 * 店铺首页接口
 * Created by KK on 2017/9/7.
 */
public interface SelectVisualNavigationAllListener extends OnBaseRequestListener {

    void onSelectVisualNavigationAllSuccess(List<ShopTab> shopTabs);

}
