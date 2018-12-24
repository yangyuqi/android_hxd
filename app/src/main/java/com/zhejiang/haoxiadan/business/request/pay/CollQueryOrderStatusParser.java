package com.zhejiang.haoxiadan.business.request.pay;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 中金快捷支付状态处理中接口
 * Created by KK on 2017/11/30.
 */
public class CollQueryOrderStatusParser extends AbsBaseParser {

    public CollQueryOrderStatusParser(OnBaseRequestListener onBaseRequestListener) {
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

        CollQueryOrderStatusListener listener = (CollQueryOrderStatusListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onCollQueryOrderStatusSuccess(price,payWay);
        }
    }

}
