package com.zhejiang.haoxiadan.business.request.pay;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 订单中金银行卡支付
 * Created by KK on 2017/9/9.
 */
public class OrderPayParser extends AbsBaseParser {

    public OrderPayParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        String price = "";
        String payWay = "";
        try {
            JSONObject jsonObject = new JSONObject(data);
            price = jsonObject.getDouble("totalPrice")+"";
            payWay = jsonObject.getString("payment_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OrderPayListener listener = (OrderPayListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onOrderPaySuccess(price,payWay);
        }
    }

}
