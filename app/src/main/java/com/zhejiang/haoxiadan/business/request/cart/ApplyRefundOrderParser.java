package com.zhejiang.haoxiadan.business.request.cart;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Reason;
import com.zhejiang.haoxiadan.model.requestData.in.DictionaryDataListData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 申请退款
 * Created by KK on 2017/9/7.
 */
public class ApplyRefundOrderParser extends AbsBaseParser {

    public ApplyRefundOrderParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {

        ApplyRefundOrderListener listener = (ApplyRefundOrderListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onApplyRefundOrderSuccess();
        }
    }

}
