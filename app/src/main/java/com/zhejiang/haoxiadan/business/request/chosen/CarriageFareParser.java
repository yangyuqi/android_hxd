package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qiuweiyu on 2017/11/25.
 */

public class CarriageFareParser extends AbsBaseParser {

    public CarriageFareParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            String shipPrice = jsonObject.getString("shipPrice");
            CarriageFareListener listener = (CarriageFareListener) mOnBaseRequestListener.get();
            if (listener!=null){
                listener.getShip(shipPrice);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
