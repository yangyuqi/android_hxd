package com.zhejiang.haoxiadan.business.request.pay;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * App支付宝微信订单支付
 * Created by KK on 2017/9/12.
 */
public class ApplyOrderFormPayParser extends AbsBaseParser {

    public ApplyOrderFormPayParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        String alipay = null;
        Map<String,String> wxpayMap = new HashMap<>();

        try {
            JSONObject jsonObject = new JSONObject(data);
            if(jsonObject.has("orderString")){
                alipay = jsonObject.getString("orderString");
            }else{//微信支付参数
                wxpayMap.put("appid",jsonObject.getString("appid"));
                wxpayMap.put("partnerid",jsonObject.getString("partnerid"));
                wxpayMap.put("prepayid",jsonObject.getString("prepayid"));
                wxpayMap.put("noncestr",jsonObject.getString("noncestr"));
                wxpayMap.put("timestamp",jsonObject.getLong("timestamp")+"");
                wxpayMap.put("package",jsonObject.getString("package"));
                wxpayMap.put("sign",jsonObject.getString("sign"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApplyOrderFormPayListener listener = (ApplyOrderFormPayListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onApplyOrderFormPaySuccess(wxpayMap,alipay);
        }
    }

}
