package com.zhejiang.haoxiadan.business.request.chosen;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.BannersData;

/**
 * Created by qiuweiyu on 2017/9/12.
 */

public interface QueryBannerListener extends OnBaseRequestListener {
    void getList(BannersData bannersData);
}
