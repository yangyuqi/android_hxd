package com.zhejiang.haoxiadan.business.request.category;

import android.content.Context;

import com.zhejiang.haoxiadan.business.BaseRequester;
import com.zhejiang.haoxiadan.third.network.Server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KK on 2017/9/5.
 */
public class CategoryRequester extends BaseRequester {


    /**
     * 普通商品分类菜单
     * @param context
     * @param listener
     */
    public void queryGoodsClassAll(Context context, QueryGoodsClassAllListener listener){
        Map<String, Object> params = new HashMap<>();
        post(context, Server.getUrl("goodsClass/queryGoodsClassAll"),params,listener,new QueryGoodsClassAllParser(listener));
    }


//    public void queryGoodsListByClassId(Context context, QueryGoodsClassAllListener listener){
//        Map<String, Object> params = new HashMap<>();
//        post(context, Server.getUrl("goodsClass/queryGoodsListByClassId"),params,listener,new QueryGoodsClassAllParser(listener));
//    }

    /**
     * 前端展示分类
     * @param context
     * @param listener
     */
    public void showViewClass(Context context, ShowViewClassListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("showType","Pc");
        post(context, "http://" + Server.HOST  + Server.APP+"/api/app/v1/showClass/showViewClass",params,listener,new ShowViewClassParser(listener));
    }
}
