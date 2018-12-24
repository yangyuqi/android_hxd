package com.zhejiang.haoxiadan.business.request.pay;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * 中金快捷支付状态处理中接口
 * Created by KK on 2017/11/30.
 */
public interface CollQueryOrderStatusListener extends OnBaseRequestListener {

    void onCollQueryOrderStatusSuccess(String price, String payWay);

}
