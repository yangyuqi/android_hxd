package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.CrowdFundRulesData;

/**
 * Created by qiuweiyu on 2017/9/13.
 */

public class FlightRuleParser extends AbsBaseParser {
    public FlightRuleParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        CrowdFundRulesData fundRulesData = mDataParser.parseObject(data,CrowdFundRulesData.class);
        FlightRuleListener listener = (FlightRuleListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.getData(fundRulesData);
        }
    }
}
