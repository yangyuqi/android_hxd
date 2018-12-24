package com.zhejiang.haoxiadan.business.request.category;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Category;

import java.util.List;

/**
 * 前端展示分类
 * Created by KK on 2017/11/23.
 */
public interface ShowViewClassListener extends OnBaseRequestListener {

    void onShowViewClassSuccess(List<Category> categories);

}
