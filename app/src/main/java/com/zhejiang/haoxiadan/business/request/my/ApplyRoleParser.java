package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Area;
import com.zhejiang.haoxiadan.model.requestData.in.SelectAreaAllData;
import com.zhejiang.haoxiadan.model.requestData.in.SelectAreaAllFatherData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 采购商/供应商申请
 * Created by KK on 2017/9/4.
 */
public class ApplyRoleParser extends AbsBaseParser {

    public ApplyRoleParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        String applyId = null;
        try {
            JSONObject jsonObject = new JSONObject(data);
            applyId = jsonObject.getString("applyId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApplyRoleListener listener = (ApplyRoleListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onApplyRoleSuccess(applyId);
        }
    }

}
