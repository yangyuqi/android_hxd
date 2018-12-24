package com.zhejiang.haoxiadan.business.request.pay;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.BankCard;
import com.zhejiang.haoxiadan.model.requestData.in.BankListData;
import com.zhejiang.haoxiadan.model.requestData.in.QueryBankListData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 绑定银行卡发短信
 * Created by KK on 2017/9/8.
 */
public class BankInfoSaveParser extends AbsBaseParser {

    public BankInfoSaveParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        String userBankId = null;
        try {
            JSONObject jsonObject = new JSONObject(data);
            userBankId = jsonObject.getInt("userBankId")+"";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        BankInfoSaveListener listener = (BankInfoSaveListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onBankInfoSaveSuccess(userBankId);
        }
    }

}
