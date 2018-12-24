package com.zhejiang.haoxiadan.business.request.pay;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * 订单中金银行卡支付
 * Created by KK on 2017/9/9.
 */
public interface OrderPayListener extends OnBaseRequestListener {

    void onOrderPaySuccess(String price,String payWay);

}
