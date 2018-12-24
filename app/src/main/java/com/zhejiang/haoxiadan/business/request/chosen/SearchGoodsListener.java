package com.zhejiang.haoxiadan.business.request.chosen;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.choseModel.SearchData;

/**
 * Created by qiuweiyu on 2017/9/6.
 */

public interface SearchGoodsListener extends OnBaseRequestListener {
    void getData(SearchData searchData);
}
