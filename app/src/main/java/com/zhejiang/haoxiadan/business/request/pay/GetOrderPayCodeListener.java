package com.zhejiang.haoxiadan.business.request.pay;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.BankCard;

import java.util.List;

/**
 * 获得订单中金银行卡支付code
 * Created by KK on 2017/9/9.
 */
public interface GetOrderPayCodeListener extends OnBaseRequestListener {

    void onGetOrderPayCodeSuccess(String code);

}
