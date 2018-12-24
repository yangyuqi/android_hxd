package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsGspData2;
import com.zhejiang.haoxiadan.model.requestData.out.DeliveryMessageBean;
import com.zhejiang.haoxiadan.model.requestData.out.my.RefundNewBean;
import com.zhejiang.haoxiadan.model.requestData.out.my.RefundNewBeanDetail;

import java.util.ArrayList;
import java.util.List;

public class RefundNewDetailsParser extends AbsBaseParser {
    public RefundNewDetailsParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        RefundNewBean bean =  mDataParser.parseObject(data,RefundNewBean.class);
        RefundNewDetailsListener listener = (RefundNewDetailsListener) mOnBaseRequestListener.get();
        if (listener!=null){
            Gson gson = new Gson();
            RefundNewBeanDetail detail = gson.fromJson(bean.getGoodsInfo(),RefundNewBeanDetail.class);
            listener.onRefundOrderFormStatusByIdSuccess( detail);
        }
    }
}
