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
 * 退款原因
 * Created by KK on 2017/9/7.
 */
public class GetRefundOrderReasonParser extends AbsBaseParser {

    public GetRefundOrderReasonParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {


        List<Reason> reasons = null;
        try {
            JSONObject jsonObject = new JSONObject(data);
            String list = jsonObject.getJSONArray("DictionaryDataList").toString();
            List<DictionaryDataListData> dictionaryDataListDatas = mDataParser.parseList(list,DictionaryDataListData.class);
            reasons = mapped(dictionaryDataListDatas);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        GetRefundOrderReasonListener listener = (GetRefundOrderReasonListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onGetRefundOrderReasonSuccess(reasons);
        }
    }

    private List<Reason> mapped(List<DictionaryDataListData> dictionaryDataListDatas){
        List<Reason> reasons = new ArrayList<>();
        for(DictionaryDataListData dictionaryDataListData: dictionaryDataListDatas){
            Reason reason = new Reason();
            reason.setId(dictionaryDataListData.getId()+"");
            reason.setTitle(dictionaryDataListData.getDataName());

            reasons.add(reason);
        }
        return reasons;
    }

}
