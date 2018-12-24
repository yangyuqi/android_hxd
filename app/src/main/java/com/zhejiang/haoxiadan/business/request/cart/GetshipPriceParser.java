package com.zhejiang.haoxiadan.business.request.cart;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Address;
import com.zhejiang.haoxiadan.model.common.CartGoods;
import com.zhejiang.haoxiadan.model.common.CartGoodsStyle;
import com.zhejiang.haoxiadan.model.requestData.in.GetshipPriceData;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsGspData;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsVoListData;
import com.zhejiang.haoxiadan.model.requestData.in.ReceiverAddrVoData;
import com.zhejiang.haoxiadan.model.requestData.in.SelectBalanceAllData;
import com.zhejiang.haoxiadan.model.requestData.in.SinglePriceListData;
import com.zhejiang.haoxiadan.util.NumberUtils;
import com.zhejiang.haoxiadan.util.TimeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取邮费接口
 * Created by KK on 2017/9/5.
 */
public class GetshipPriceParser extends AbsBaseParser {

    public GetshipPriceParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        GetshipPriceData getshipPriceData = mDataParser.parseObject(data,GetshipPriceData.class);

        double shipPrice = NumberUtils.getDoubleFromString(getshipPriceData.getShipPrice());
        List<Map<String,String>> singleList = new ArrayList<>();
        if (getshipPriceData.getSinglePriceList()!=null){
            for(SinglePriceListData singlePriceListData:getshipPriceData.getSinglePriceList()){
                Map<String,String> single = new HashMap<>();
                single.put("goodsId",singlePriceListData.getGoodsId());
                single.put("shipPrice",singlePriceListData.getSingleGoodsPrice()+"");
                singleList.add(single);
            }
        }

        GetshipPriceListener listener = (GetshipPriceListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onGetshipPriceSuccess(singleList,shipPrice);
        }
    }


}
