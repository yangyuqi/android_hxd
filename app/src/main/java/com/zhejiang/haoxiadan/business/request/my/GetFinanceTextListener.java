package com.zhejiang.haoxiadan.business.request.my;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.my.FinanceTextData;

/**
 * Created by qiuweiyu on 2017/9/22.
 */

public interface GetFinanceTextListener extends OnBaseRequestListener {
    void getData(FinanceTextData financeTextData);
}
