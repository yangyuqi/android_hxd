package com.zhejiang.haoxiadan.business.request.chosen;

import android.content.Context;

import com.zhejiang.haoxiadan.business.BaseRequester;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.choseModel.CarriageModel;
import com.zhejiang.haoxiadan.model.requestData.in.FavoritrBean;
import com.zhejiang.haoxiadan.model.requestData.in.chose.AddToCartBean;
import com.zhejiang.haoxiadan.third.network.Server;
import com.zhejiang.haoxiadan.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qiuweiyu on 2017/9/1.
 */

public class GoodsRequester extends BaseRequester {

    public void getGoodsDetails(Context context ,String accessToken ,String id ,GoodsDetailsListener listener){
        Map<String,Object> map = new HashMap<>();
        if (!accessToken.equals("")){
            map.put("accessToken",accessToken);
        }
        map.put("goodsId",id);
        post(context, Server.getUrl("goods/queryGoodsById"),map,listener,new GoodsDetailsParser(listener));
    }

    public void getGoodsDetailsWorsted(Context context ,String accessToken ,String id ,GoodsDetailsListener listener){
        Map<String,Object> map = new HashMap<>();
        if (!accessToken.equals("")){
            map.put("accessToken",accessToken);
        }
        map.put("goodsId",id);
        map.put("addType","app");
        post(context, "http://218.94.47.138:8905/TermireMall/api/app/v3/goods/queryWorstedById",map,listener,new GoodsDetailsParser(listener));
    }

    public void getTradeInfo(Context context ,GetTradeInfoListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken", SharedPreferencesUtil.get(context, Constants.accessToken,""));
        post(context,Server.getUrl("trade/queryTradeInfo"),map,listener,new GetTradeInfoParser(listener));
    }

    public void getGoodsFloor(Context context , String accessToken ,String tradeId ,FloorListener listener){
        Map<String,Object> map = new HashMap<>();
        if (!accessToken.equals("")) {
            map.put("accessToken", accessToken);
        }
        map.put("tradeId",tradeId);
        post(context,Server.getUrl("goodsfloor/selectGoodsFloorAllForMobileNew"),map,listener,new FloorParser(listener));
    }

    public void newMyFooter(Context context , String accessToken ,String goodsId ,String storeId ,NewMyFooterListener listener){
        if (!accessToken.equals("")) {
            Map<String, Object> map = new HashMap<>();
            map.put("accessToken", accessToken);
            map.put("goodsId", goodsId);
            map.put("storeId", storeId);
            post(context, Server.getUrl("footPrint/addFootPrint"), map, listener, new NewMyFooterParser(listener));
        }
    }

    public void changeFavorite(Context context , String accessToken , List<FavoritrBean> bean ,FavoriteListener listener){
        if (!accessToken.equals("")) {
            Map<String, Object> map = new HashMap<>();
            map.put("accessToken",accessToken);
            map.put("favoriteGoodsList",bean);
            post(context,Server.getUrl("favorite/addFavoriteGoods"),map,listener,new FavoriteParser(listener));
        }
    }

    public void addToCart(Context context , String accessToken , String storeId , String goodsType , List<AddToCartBean> goodsList ,AddToCartListener listener){
        if (!accessToken.equals("")){
            Map<String ,Object> map = new HashMap<>();
            map.put("accessToken",accessToken);
            map.put("storeId",storeId);
            map.put("goodsType",goodsType);
            map.put("goodsList",goodsList);
            post(context,Server.getUrl("goodsCart/addGoodsCart"),map,listener,new AddToCartParser(listener));
        }
    }

    public void getGoodsList(Context context ,List<String> goodsIdlist ,GetGoodsListListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("goodsIdList",goodsIdlist);
        post(context,Server.getUrl("goods/queryListByGoodsIds"),map,listener,new GetGoodsListParser(listener));
    }


    public void topInfo(Context context ,String accessToken ,TopInfoListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        post(context,Server.getUrl("hotsport/topInfo"),map,listener,new TopInfoParser(listener));
    }

    public void topInfoDetails(Context context,String accessToken ,int topId ,TopInfoDetailsListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("topId",topId);
        post(context,Server.getUrl("hotsport/topInfoContent"),map,listener,new TopInfoDetailsParser(listener));
    }

    public void searchGoodsList(Context context ,String searchType ,String keyWord ,String orderBy ,int currentPage ,int showCount ,SearchGoodsListener listener){
        Map<String ,Object> map = new HashMap<>();
        map.put("searchType",searchType);
        map.put("keyWord",keyWord);
        map.put("orderBy",orderBy);
        map.put("currentPage",currentPage);
        map.put("showCount",showCount);
        if (orderBy.equals("storePrice")) {
            map.put("orderType", "asc");
        }else if (orderBy.equals("goodsSalenum")){
            map.put("orderType", "desc");
        }else if (orderBy.equals("colligate")){
            map.put("orderType", "desc");
        }
        post(context,Server.searchGetUrl("lucene/searchByLucene"),map,listener,new SearchGoodsParser(listener));
    }

    public void searchNewGoodslist(Context context ,Map<String,Object> map ,SearchGoodsListener listener){
        post(context,Server.searchGetUrl("lucene/searchByLucene"),map,listener,new SearchGoodsParser(listener));

    }


    public void searchBrandList(Context context ,String searchType ,String brandName ,String orderBy ,int currentPage ,int showCount ,SearchGoodsListener listener){
            Map<String ,Object> map = new HashMap<>();
        map.put("searchType",searchType);
        map.put("brandName",brandName);
        map.put("orderBy",orderBy);
        map.put("currentPage",currentPage);
        map.put("showCount",showCount);
        post(context,Server.searchGetUrl("lucene/searchByLucene"),map,listener,new SearchGoodsParser(listener));
    }

    public void searchFactort(Context context ,String searchType ,String keyWord ,String mainIndustry ,int currentPage ,int showCount ,String orderBy,String orderTypr ,SearchGoodsListener listener){
        Map<String ,Object> map = new HashMap<>();
        map.put("searchType",searchType);

        if (!keyWord.equals("")){
            map.put("keyWord",keyWord);
        }

        if (!mainIndustry.equals("")) {
            map.put("mainIndustry", mainIndustry);
        }
        if (!orderBy.equals("")){
            map.put("orderBy",orderBy);
        }
        map.put("currentPage",currentPage);
        map.put("showCount",showCount);
        map.put("orderType",orderTypr);
        post(context,Server.searchGetUrl("lucene/searchByLucene"),map,listener,new SearchGoodsParser(listener));
    }

    public void getCategoryList(Context context ,String searchType ,String gcIdThird ,String orderBy ,int currentPage ,int showCount ,SearchGoodsListener listener){
        Map<String ,Object> map = new HashMap<>();
        map.put("searchType",searchType);
        map.put("gcIdThird",gcIdThird);
        map.put("orderBy",orderBy);
        map.put("currentPage",currentPage);
        map.put("showCount",showCount);
        if (orderBy.equals("goodsSalenum")||orderBy.equals("colligate")) {
            map.put("orderType", "desc");
        }else {
            map.put("orderType","asc");
        }
        post(context,Server.searchGetUrl("lucene/searchByLucene"),map,listener,new SearchGoodsParser(listener));
    }

    /**
     * 猜你喜欢
     * @param context
     * @param goodsId
     * @param pageNo
     * @param pageSize
     * @param listener
     */
    public void guessYouLike(Context context ,String goodsId,int pageNo ,int pageSize ,GuessYouLikeListener listener){
        Map<String ,Object> map = new HashMap<>();
        map.put("id",goodsId);
        map.put("currentPage",pageNo);
        map.put("showCount",pageSize);
        post(context,Server.getUrl("guess/guessYouLike"),map,listener,new GuessYouLikeParser(listener));
    }

    public void mobileTitleInfo(Context context ,MobileTitleInfoListener listener){
        Map<String ,Object> map = new HashMap<>();
        post(context,Server.getUrl("MobileTitleInfo/selectMobileTitleInfoNew"),map,listener,new MobileTitleInfoParser(listener));
    }

    public void queryBanner(Context context ,QueryBannerListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("type","Mobile");
        post(context,Server.getUrl("banner/queryBannerByChannelId"),map,listener,new QueryBannerParser(listener));
    }

    public void getDiscuss(Context context ,String goodsId ,int currentPage ,int showCount ,GetDiscussListener listener ){
        Map<String,Object> map = new HashMap<>();
        map.put("goodsId",goodsId);
        map.put("currentPage",currentPage);
        map.put("showCount",showCount);
        post(context,Server.getUrl("evaluate/selectEvaluateAllByGoodsId"),map,listener,new GetDiscussParser(listener));
    }

    public void flightRule(Context context ,String goodsId ,FlightRuleListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("goodsId",goodsId);
        post(context,Server.getUrl("goods/queryGoodsRulesById"),map,listener,new FlightRuleParser(listener));
    }

    public void prefenceFactory(Context context ,int currentPage ,int showCount ,String orderType ,String mainIndustryId, PrefenceFactoryListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("currentPage",currentPage);
        map.put("showCount",showCount);
        if (orderType!=null){
            map.put("OrderType",orderType);
        }
        if (mainIndustryId!=null){
            map.put("mainIndustryId",mainIndustryId);
        }
        post(context,Server.getUrl("store/SelectStoreList"),map,listener,new PrefenceFactoryParser(listener));
    }

    public void channelInfo(Context context , String chld , ChannelInfoListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("chId",chld);
        post(context,Server.getUrl("channelfloor/selectChannelsFloorAllForApp"),map,listener,new ChannelInfoParser(listener));

    }

    public void getSystemTime(Context context ,GetSystemTimeListener listener ){
        Map<String,Object> map = new HashMap<>();
        post(context,Server.getUrl("goodsCart/getNow"),map,listener,new GetSystemTimeParser(listener));
    }

    public void getCarriageFare(Context context , String cityName ,String cityId , ArrayList<CarriageModel> model ,CarriageFareListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("cityName",cityName);
        map.put("cityId",cityId);
        map.put("goodsList",model);
        post(context,Server.getUrl("goodsDetail/getgoodsDetailshipPrice"),map,listener,new CarriageFareParser(listener));
    }

    public void getHasCommond(Context context , String commond , HasCommondLiatener liatener){
        Map<String,Object> map = new HashMap<>();
        map.put("haoCommand",commond);
        post(context,Server.getUrl("goods/haoCommand"),map,liatener,new HasCommondParser(liatener));
    }
}
