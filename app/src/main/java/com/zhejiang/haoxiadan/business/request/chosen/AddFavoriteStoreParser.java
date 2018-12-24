package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Supplier;
import com.zhejiang.haoxiadan.model.requestData.in.QueryStoreArchivesData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 新增收藏夹店铺信息,再次点击则取消收藏
 * Created by KK on 2017/9/8.
 */
public class AddFavoriteStoreParser extends AbsBaseParser {
    public AddFavoriteStoreParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        AddFavoriteStoreListener listener = (AddFavoriteStoreListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onAddFavoriteStoreSuccess();
        }
    }
}
