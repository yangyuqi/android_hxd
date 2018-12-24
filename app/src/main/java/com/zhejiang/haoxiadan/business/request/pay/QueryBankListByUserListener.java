package com.zhejiang.haoxiadan.business.request.pay;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.BankCard;

import java.util.List;

/**
 * 获取用户已绑定银行卡列表
 * Created by KK on 2017/9/9.
 */
public interface QueryBankListByUserListener extends OnBaseRequestListener {

    void onQueryBankListByUserSuccess(List<BankCard> bankCards);

}
