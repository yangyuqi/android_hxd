package com.zhejiang.haoxiadan.business.request.pay;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.BankCard;

import java.util.List;

/**
 * 绑定银行卡发短信
 * Created by KK on 2017/9/8.
 */
public interface BankInfoSaveListener extends OnBaseRequestListener {

    void onBankInfoSaveSuccess(String userBankId);

}
