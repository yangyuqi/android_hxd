package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;
import android.util.Log;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.choseModel.GoodsIdListBeanData;
import com.zhejiang.haoxiadan.model.choseModel.GoodsListData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/5.
 */

public class GetGoodsListParser extends AbsBaseParser {
    public GetGoodsListParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        GoodsListData listData = null ;
        if (!data.equals("{}")) {
             listData = mDataParser.parseObject(data, GoodsListData.class);
        }else {
            List<GoodsIdListBeanData> dataList = new ArrayList<>();
            listData = new GoodsListData(dataList);
        }
        GetGoodsListListener listListener = (GetGoodsListListener) mOnBaseRequestListener.get();
        if (listListener!=null){
            listListener.getGoodsList(listData);
        }
    }
}
