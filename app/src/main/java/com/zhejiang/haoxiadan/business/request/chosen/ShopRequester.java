package com.zhejiang.haoxiadan.business.request.chosen;

import android.content.Context;

import com.zhejiang.haoxiadan.business.BaseRequester;
import com.zhejiang.haoxiadan.third.network.Server;

import java.util.HashMap;
import java.util.Map;

/**
 * 店铺
 * Created by KK on 2017/9/7.
 */
public class ShopRequester extends BaseRequester {

    /**
     * 获取商家信息
     * @param context
     * @param accessToken
     * @param storeId
     * @param listener
     */
    public void queryStoreById(Context context, String accessToken,String storeId, QueryStoreByIdListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        params.put("storeId",storeId);
        post(context, Server.getUrl("store/queryStoreById"),params,listener,new QueryStoreByIdParser(listener));
    }

    /**
     * 获取商家档案
     * @param context
     * @param accessToken
     * @param storeId
     * @param listener
     */
    public void queryStoreArchives(Context context, String accessToken,String storeId, QueryStoreArchivesListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        params.put("storeId",storeId);
        post(context, Server.getUrl("store/queryStoreArchives"),params,listener,new QueryStoreArchivesParser(listener));
    }


    /**
     * 店铺首页接口
     * @param context
     * @param storeId
     * @param listener
     */
    public void selectVisualNavigationAll(Context context,String storeId, SelectVisualNavigationAllListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("storeId",storeId);
        params.put("decorationType","WAP");
        post(context, Server.getUrl("visualNavigation/selectVisualNavigationAll"),params,listener,new SelectVisualNavigationAllParser(listener));
    }

    /**
     * 店铺首页楼层
     * @param context
     * @param storeId
     * @param listener
     */
    public void selectVisualFloorAll(Context context,String storeId, SelectVisualFloorAllListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("storeId",storeId);
        params.put("decorationType","WAP");
        post(context, Server.getUrl("visualFloor/selectVisualFloorAll"),params,listener,new SelectVisualFloorAllParser(listener));
    }

    /**
     *
     * @param context
     * @param accessToken
     * @param storeId
     * @param listener
     */
    public void addFavoriteStore(Context context, String accessToken,String storeId, AddFavoriteStoreListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        params.put("storeId",storeId);
        post(context, Server.getUrl("favorite/addFavoriteStore"),params,listener,new AddFavoriteStoreParser(listener));
    }

    /**
     * 店铺内搜索
     * @param context
     * @param storeId
     * @param orderBy
     * @param currentPage
     * @param showCount
     * @param listener
     */
    public void shopGoodsSearch(Context context ,String storeId ,String key ,String orderBy ,int currentPage ,int showCount ,String orderType ,SearchShopGoodsListener listener){
        Map<String ,Object> map = new HashMap<>();
        map.put("keyWord",key);
        map.put("searchType","goods");
        map.put("currentPage",currentPage);
        map.put("showCount",showCount);
        map.put("storeId",storeId);
        switch (orderBy){
            case "goodsSalenum":
                map.put("orderBy",orderBy);
                map.put("orderType","desc");
                break;
            case "storePrice":
                map.put("orderBy",orderBy);
                map.put("orderType",orderType);
                break;
            case "colligate":
                map.put("orderBy",orderBy);
                map.put("orderType","desc");
                break;
        }
        post(context,Server.searchGetUrl("lucene/searchByLucene"),map,listener,new SearchShopGoodsParser(listener));
    }
}
