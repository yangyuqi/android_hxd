package com.zhejiang.haoxiadan.business.request.chosen;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.PrefenceFactoryData;

/**
 * Created by qiuweiyu on 2017/9/15.
 */

public interface PrefenceFactoryListener extends OnBaseRequestListener {
    void getData(PrefenceFactoryData factoryData);
}
