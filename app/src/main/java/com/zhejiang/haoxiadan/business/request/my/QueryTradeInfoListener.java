package com.zhejiang.haoxiadan.business.request.my;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Industry;
import com.zhejiang.haoxiadan.model.common.Purchaser;
import com.zhejiang.haoxiadan.model.common.Supplier;
import com.zhejiang.haoxiadan.ui.activity.my.ValueAddActivity;

import java.util.List;

/**
 * 行业表
 * Created by KK on 2017/9/4.
 */
public interface QueryTradeInfoListener extends OnBaseRequestListener {

    /**
     * @param industries 全部行业
     * @param firstFocus 主采购行业（如果登录了）
     * @param secondFocus 还关注行业（如果登录了）
     */
    void onQueryTradeInfoSuccess(List<Industry> industries,List<Industry> firstFocus,List<Industry> secondFocus);

}
