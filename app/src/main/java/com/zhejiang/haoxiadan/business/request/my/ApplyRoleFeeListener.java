package com.zhejiang.haoxiadan.business.request.my;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Area;
import com.zhejiang.haoxiadan.model.common.YearFee;

import java.util.List;

/**
 * 获得采购商/供应商申请费用
 * Created by KK on 2017/9/4.
 */
public interface ApplyRoleFeeListener extends OnBaseRequestListener {

    void onApplyRoleFeeSuccess(List<YearFee> yearFees);

}
