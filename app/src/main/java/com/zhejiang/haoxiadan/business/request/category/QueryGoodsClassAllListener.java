package com.zhejiang.haoxiadan.business.request.category;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Category;
import com.zhejiang.haoxiadan.model.common.YearFee;

import java.util.List;

/**
 * 普通商品分类菜单
 * Created by KK on 2017/9/5.
 */
public interface QueryGoodsClassAllListener extends OnBaseRequestListener {

    void onQueryGoodsClassAllSuccess(List<Category> categories);

}
