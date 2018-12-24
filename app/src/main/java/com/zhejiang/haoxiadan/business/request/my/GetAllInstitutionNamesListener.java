package com.zhejiang.haoxiadan.business.request.my;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.my.NamesBean;

import java.util.List;

/**
 * 获取贷款机构
 * Created by KK on 2017/9/20.
 */

public interface GetAllInstitutionNamesListener extends OnBaseRequestListener{
    void onSuccess(List<NamesBean> beanList);
}
