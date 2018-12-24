package com.zhejiang.haoxiadan.business.request.my;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.in.AreaInfoBean;

/**
 * Created by qiuweiyu on 2017/9/5.
 */

public interface InitAddressListener extends OnBaseRequestListener {
    void getAddress(String bean);
}
