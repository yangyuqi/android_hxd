package com.zhejiang.haoxiadan.business.request.chosen;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.choseModel.CommondGoodsBean;

/**
 * Created by qiuweiyu on 2017/12/25.
 */

public interface HasCommondLiatener extends OnBaseRequestListener {
    void onGetData(CommondGoodsBean goodsBean);
}
