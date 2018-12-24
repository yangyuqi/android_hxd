package com.zhejiang.haoxiadan.business.request.pay;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * 获得增值服务中金银行卡支付code
 * Created by KK on 2017/9/9.
 */
public interface GetValueAddPayCodeListener extends OnBaseRequestListener {

    void onGetValueAddPayCodeSuccess();

}
