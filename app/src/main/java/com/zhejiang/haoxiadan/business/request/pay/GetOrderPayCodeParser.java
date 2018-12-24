package com.zhejiang.haoxiadan.business.request.pay;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.BankCard;
import com.zhejiang.haoxiadan.model.requestData.in.BankListData;
import com.zhejiang.haoxiadan.model.requestData.in.QueryBankListByUserData;

import java.util.ArrayList;
import java.util.List;

/**
 * 获得订单中金银行卡支付code
 * Created by KK on 2017/9/9.
 */
public class GetOrderPayCodeParser extends AbsBaseParser {

    public GetOrderPayCodeParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {

        GetOrderPayCodeListener listener = (GetOrderPayCodeListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onGetOrderPayCodeSuccess("");
        }
    }

}
