package com.zhejiang.haoxiadan.business.request.pay;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.BankCard;

import java.util.List;

/**
 * 验证绑定短信验证码
 * Created by KK on 2017/9/8.
 */
public interface VerifySBindingSMSListener extends OnBaseRequestListener {

    void onVerifySBindingSMSSuccess();

}
