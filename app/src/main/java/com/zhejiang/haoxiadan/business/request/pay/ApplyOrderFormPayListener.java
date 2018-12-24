package com.zhejiang.haoxiadan.business.request.pay;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

import java.util.Map;

/**
 * App支付宝微信订单支付
 * Created by KK on 2017/9/12.
 */
public interface ApplyOrderFormPayListener extends OnBaseRequestListener {

    void onApplyOrderFormPaySuccess(Map<String,String> wxpayMap,String alipayInfo);

}
