package com.zhejiang.haoxiadan.business.request.my;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.my.CurrentUserAddressBean;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/4.
 */

public interface GetAllAddressListener  extends OnBaseRequestListener{
    void getUserAddress(List<CurrentUserAddressBean> beanList);
}
