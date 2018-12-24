package com.zhejiang.haoxiadan.business.request.chosen;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.DiscussContentData;

/**
 * Created by qiuweiyu on 2017/9/12.
 */

public interface GetDiscussListener extends OnBaseRequestListener {
    void getDiscuss(DiscussContentData contentData);
}
