package com.zhejiang.haoxiadan.business.request.chosen;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.CrowdFundRulesData;

/**
 * Created by qiuweiyu on 2017/9/13.
 */

public interface FlightRuleListener extends OnBaseRequestListener {
    void getData(CrowdFundRulesData data);
}
