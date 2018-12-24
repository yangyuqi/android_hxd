package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.business.request.my.ApplyRoleInfoListener;
import com.zhejiang.haoxiadan.model.common.Shop;
import com.zhejiang.haoxiadan.model.requestData.in.QueryStoreByIdData;

/**
 * 获取商家信息
 * Created by KK on 2017/9/7.
 */
public class QueryStoreByIdParser extends AbsBaseParser {
    public QueryStoreByIdParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        QueryStoreByIdData queryStoreByIdData = mDataParser.parseObject(data,QueryStoreByIdData.class);

        Shop shop = mapped(queryStoreByIdData);

        QueryStoreByIdListener listener = (QueryStoreByIdListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onQueryStoreByIdSuccess(shop);
        }
    }

    private Shop mapped(QueryStoreByIdData queryStoreByIdData){
        Shop shop = new Shop();
        shop.setId(queryStoreByIdData.getStoreId()+"");
        shop.setIcon(queryStoreByIdData.getStoreLogo());
        shop.setName(queryStoreByIdData.getStoreName());
        shop.setPhone(queryStoreByIdData.getStoreTelephoneas());
        shop.setMainIndustry(queryStoreByIdData.getMainIndustry());
        shop.setBgImg(queryStoreByIdData.getStorePhotoBg());
        shop.setStoreArea(queryStoreByIdData.getStoreArea());
        if(queryStoreByIdData.getIsCollect()!=null){
            switch (queryStoreByIdData.getIsCollect()){
                case "0":
                    shop.setCollect(false);
                    break;
                case "1":
                    shop.setCollect(true);
                    break;
            }
        }else{
            shop.setCollect(false);
        }

        return shop;
    }
}
