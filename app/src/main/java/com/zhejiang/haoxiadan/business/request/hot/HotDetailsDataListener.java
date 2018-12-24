package com.zhejiang.haoxiadan.business.request.hot;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.hot.HotHotData;

/**
 * Created by qiuweiyu on 2017/9/7.
 */

public interface HotDetailsDataListener extends OnBaseRequestListener {
    void getString(HotHotData s);
}
