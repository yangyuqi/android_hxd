package com.zhejiang.haoxiadan.business.request.my;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * Created by qiuweiyu on 2017/9/9.
 */

public interface ArticleListener extends OnBaseRequestListener {
    void onArticleSuccess(String str);
}
