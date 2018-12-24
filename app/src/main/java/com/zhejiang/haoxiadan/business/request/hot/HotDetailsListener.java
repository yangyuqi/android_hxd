package com.zhejiang.haoxiadan.business.request.hot;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.hot.HotspotInfoListBean;

/**
 * Created by qiuweiyu on 2017/9/7.
 */

public interface HotDetailsListener extends OnBaseRequestListener {
    void onGetData(HotspotInfoListBean listBean);
}
