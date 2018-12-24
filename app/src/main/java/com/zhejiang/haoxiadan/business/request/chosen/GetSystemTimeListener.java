package com.zhejiang.haoxiadan.business.request.chosen;

import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * Created by qiuweiyu on 2017/11/2.
 */

public interface GetSystemTimeListener extends OnBaseRequestListener {
    void ongetTime(String data);
}
