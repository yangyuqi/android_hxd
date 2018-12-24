package com.zhejiang.haoxiadan.business.request.pay;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * 增值服务中金银行卡支付
 * Created by KK on 2017/9/9.
 */
public interface ValueAddPayListener extends OnBaseRequestListener {

    void onValueAddPaySuccess();

}
