package com.zhejiang.haoxiadan.business.request.hot;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.hot.HotspotTypeList;

/**
 * Created by qiuweiyu on 2017/9/4.
 */

public interface HotListListener extends OnBaseRequestListener {
    void onGetData(HotspotTypeList list);
}
