package com.zhejiang.haoxiadan.business.request.chosen;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.TopInfoList;

/**
 * Created by qiuweiyu on 2017/9/8.
 */

public interface TopInfoListener extends OnBaseRequestListener{
    void getTopInfo(TopInfoList list);
}
