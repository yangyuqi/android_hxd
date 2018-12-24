package com.zhejiang.haoxiadan.business.request.hot;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * Created by qiuweiyu on 2017/11/9.
 */

public interface WelcomePhotoListener extends OnBaseRequestListener {
    void onGetPhoto(String data);
}
