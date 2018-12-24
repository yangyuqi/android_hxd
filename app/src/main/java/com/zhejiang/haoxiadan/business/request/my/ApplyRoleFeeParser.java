package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Area;
import com.zhejiang.haoxiadan.model.common.YearFee;
import com.zhejiang.haoxiadan.model.requestData.in.ApplyRoleFeeData;
import com.zhejiang.haoxiadan.model.requestData.in.FeeData;
import com.zhejiang.haoxiadan.model.requestData.in.SelectAreaAllData;
import com.zhejiang.haoxiadan.model.requestData.in.SelectAreaAllFatherData;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取所有区域接口
 * Created by KK on 2017/9/4.
 */
public class ApplyRoleFeeParser extends AbsBaseParser {

    public ApplyRoleFeeParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        ApplyRoleFeeData applyRoleFeeData = mDataParser.parseObject(data,ApplyRoleFeeData.class);

        List<YearFee> yearFees = mapped(applyRoleFeeData.getList());
        ApplyRoleFeeListener listener = (ApplyRoleFeeListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onApplyRoleFeeSuccess(yearFees);
        }
    }

    private List<YearFee> mapped(List<FeeData> feeDatas){
        List<YearFee> yearFees = new ArrayList<>();
        for(FeeData feeData : feeDatas){
            YearFee yearFee = new YearFee();
            yearFee.setId(feeData.getId());
            yearFee.setName(feeData.getTitle());
            yearFee.setYear(feeData.getYear());
            yearFee.setPrice(feeData.getPrice());
            yearFees.add(yearFee);
        }
        return yearFees;
    }

}
