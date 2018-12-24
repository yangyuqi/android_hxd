package com.zhejiang.haoxiadan.business.request.my;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

public interface CloudPayListener extends OnBaseRequestListener{
    void onSuccsee(String data);
}
