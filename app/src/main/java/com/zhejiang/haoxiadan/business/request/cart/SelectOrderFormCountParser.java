package com.zhejiang.haoxiadan.business.request.cart;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.ConsultHistory;
import com.zhejiang.haoxiadan.model.common.RefundDetail;
import com.zhejiang.haoxiadan.model.common.Supplier;
import com.zhejiang.haoxiadan.model.requestData.in.RefundApplyFormListData;
import com.zhejiang.haoxiadan.model.requestData.in.RefundOrderFormStatusByIdData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询各订单状态下的订单数量
 * Created by KK on 2017/9/11.
 */
public class SelectOrderFormCountParser extends AbsBaseParser {

    public SelectOrderFormCountParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        int waitPayCount = 0;//待付款数量
        int WaitDelivOrderCount = 0;//待发货数量
        int waitGetorderCount = 0;//待收货数量
        int waitEvaorderCount = 0;//待评价数量
        int refundOrderCount = 0;//退款退货数量

        try {
            JSONObject jsonObject = new JSONObject(data);
            waitPayCount = jsonObject.getInt("waitPayCount");
            WaitDelivOrderCount = jsonObject.getInt("WaitDelivOrderCount");
            waitGetorderCount = jsonObject.getInt("waitGetorderCount");
            waitEvaorderCount = jsonObject.getInt("waitEvaorderCount");
            refundOrderCount = jsonObject.getInt("refundOrderCount");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SelectOrderFormCountListener listener = (SelectOrderFormCountListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onSelectOrderFormCountSuccess(waitPayCount,WaitDelivOrderCount,waitGetorderCount,waitEvaorderCount,refundOrderCount);
        }
    }



}
