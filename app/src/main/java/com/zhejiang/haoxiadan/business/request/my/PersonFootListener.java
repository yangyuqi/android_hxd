package com.zhejiang.haoxiadan.business.request.my;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.PersonFooter;

/**
 * Created by qiuweiyu on 2017/8/31.
 */

public interface PersonFootListener extends OnBaseRequestListener {
    void getFooter(PersonFooter footerData);
}
