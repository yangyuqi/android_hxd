package com.zhejiang.haoxiadan.business.request.cart;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Order;
import com.zhejiang.haoxiadan.model.requestData.in.SelectBalanceAllData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 新增订单
 * Created by KK on 2017/9/5.
 */
public class AddOrderFormParser extends AbsBaseParser {

    public AddOrderFormParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        String orderId = "";
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("ids");
            if (jsonArray != null && jsonArray.length()>0){
                for(int i=0;i<jsonArray.length();i++){
                    orderId = orderId +jsonArray.getString(0)+",";
                }
                if(!"".equals(orderId)){
                    orderId = orderId.substring(0,orderId.length()-1);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AddOrderFormListener listener = (AddOrderFormListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onAddOrderFormSuccess(orderId);
        }
    }
}
