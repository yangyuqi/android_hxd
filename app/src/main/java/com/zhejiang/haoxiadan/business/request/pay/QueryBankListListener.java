package com.zhejiang.haoxiadan.business.request.pay;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.BankCard;
import com.zhejiang.haoxiadan.model.common.Category;

import java.util.List;

/**
 * 获取支持的银行卡列表
 * Created by KK on 2017/9/8.
 */
public interface QueryBankListListener extends OnBaseRequestListener {

    void onQueryBankListSuccess(List<BankCard> bankCards);

}
