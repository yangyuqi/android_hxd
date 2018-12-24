package com.zhejiang.haoxiadan.business.request.my;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.User;

/**
 * Created by qiuweiyu on 2017/8/31.
 */

public interface LoginListener extends OnBaseRequestListener {
    void getUser(User user);
}
