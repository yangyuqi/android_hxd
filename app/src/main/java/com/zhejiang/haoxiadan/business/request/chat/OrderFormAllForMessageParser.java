package com.zhejiang.haoxiadan.business.request.chat;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.OrderMessage;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by wqd on 2017/9/7 0006.
 */

public class OrderFormAllForMessageParser extends AbsBaseParser {

    public OrderFormAllForMessageParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }
    @Override
    protected void parseData(@Nullable String data) {
        OrderFormAllForMessageListener orderFormAllForMessageListener = (OrderFormAllForMessageListener) mOnBaseRequestListener.get();
        try {
            JSONObject getOrderMessage = new JSONObject(data);
            List<OrderMessage> pushList = mDataParser.parseList(getOrderMessage.getString("orderFormList"), OrderMessage.class);
            if (orderFormAllForMessageListener != null) {
                orderFormAllForMessageListener.onGetPushInfoSuccess(pushList);
            }
        }catch (Exception e){
            e.printStackTrace();
            if (orderFormAllForMessageListener != null) {
                orderFormAllForMessageListener.onBusinessFail("","");
            }
        }
    }
}
