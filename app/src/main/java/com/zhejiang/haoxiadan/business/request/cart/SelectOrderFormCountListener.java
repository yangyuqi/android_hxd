package com.zhejiang.haoxiadan.business.request.cart;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.ConsultHistory;
import com.zhejiang.haoxiadan.model.common.RefundDetail;

import java.util.List;

/**
 * 查询各订单状态下的订单数量
 * Created by KK on 2017/9/11.
 */
public interface SelectOrderFormCountListener extends OnBaseRequestListener {

    void onSelectOrderFormCountSuccess(int waitPayNum,int waitSendNum,int waitReceiveNum,int waitEvaluateNum,int afterSaleNum);

}
