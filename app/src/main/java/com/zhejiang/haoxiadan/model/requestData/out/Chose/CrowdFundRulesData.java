package com.zhejiang.haoxiadan.model.requestData.out.Chose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/13.
 */

public class CrowdFundRulesData implements Serializable{
    private List<CrowdFundRulesBean> crowdFundRules ;

    public List<CrowdFundRulesBean> getCrowdFundRules() {
        return crowdFundRules;
    }

    public void setCrowdFundRules(List<CrowdFundRulesBean> crowdFundRules) {
        this.crowdFundRules = crowdFundRules;
    }
}
