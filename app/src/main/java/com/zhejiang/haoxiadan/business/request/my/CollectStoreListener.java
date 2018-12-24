package com.zhejiang.haoxiadan.business.request.my;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.my.CollectStoreData;

/**
 * Created by qiuweiyu on 2017/9/1.
 */

public interface CollectStoreListener extends OnBaseRequestListener {
        void getData(CollectStoreData collectStoreData);
}
