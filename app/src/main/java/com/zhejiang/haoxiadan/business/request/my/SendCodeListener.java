package com.zhejiang.haoxiadan.business.request.my;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * Created by qiuweiyu on 2017/8/31.
 */

public interface SendCodeListener extends OnBaseRequestListener{
    void getCode();
    void getError(String msg);
}
