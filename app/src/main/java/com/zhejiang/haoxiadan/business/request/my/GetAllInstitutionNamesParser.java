package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.my.NamesBean;
import com.zhejiang.haoxiadan.model.requestData.out.my.NamesListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取贷款机构
 * Created by KK on 2017/9/20.
 */

public class GetAllInstitutionNamesParser extends AbsBaseParser {
    public GetAllInstitutionNamesParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        List<NamesBean> beanList = new ArrayList<>();
        NamesListBean listBean = mDataParser.parseObject(data,NamesListBean.class);
        beanList.addAll(listBean.getNames());

        GetAllInstitutionNamesListener listener = (GetAllInstitutionNamesListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.onSuccess(beanList);
        }
    }
}
