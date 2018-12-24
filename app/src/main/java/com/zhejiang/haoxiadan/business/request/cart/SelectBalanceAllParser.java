package com.zhejiang.haoxiadan.business.request.cart;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Address;
import com.zhejiang.haoxiadan.model.common.CartGoods;
import com.zhejiang.haoxiadan.model.common.CartGoodsStyle;
import com.zhejiang.haoxiadan.model.common.LevelPrice;
import com.zhejiang.haoxiadan.model.requestData.in.CartGoodsNew;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsGspData;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsListsData;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsVoListData;
import com.zhejiang.haoxiadan.model.requestData.in.ReceiverAddrVoData;
import com.zhejiang.haoxiadan.model.requestData.in.SelectBalanceAllData;
import com.zhejiang.haoxiadan.util.NumberUtils;
import com.zhejiang.haoxiadan.util.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 查看修改购物车结算状态后数据列表
 * Created by KK on 2017/9/1.
 */
public class SelectBalanceAllParser extends AbsBaseParser {

    public SelectBalanceAllParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        SelectBalanceAllData selectBalanceAllData = mDataParser.parseObject(data,SelectBalanceAllData.class);

        Address address = mappedAddress(selectBalanceAllData.getReceiverAddrVo());
        List<CartGoods> cartGoodsList = mappedList(selectBalanceAllData);
        List<CartGoods> goodsList = mappedListNew(selectBalanceAllData);

        SelectBalanceAllListener listener = (SelectBalanceAllListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onSelectBalanceAllSuccess(address,goodsList);
        }
    }

    private List<CartGoods> mappedListNew(SelectBalanceAllData selectBalanceAllData) {
        List<CartGoods> cartGoodsList = new ArrayList<>();
        for(GoodsListsData goodsCartListData:selectBalanceAllData.getGoodsVoList()){
            CartGoods cartGoods = new CartGoods();
            cartGoods.setStoreId(goodsCartListData.getStoreId()+"");
            cartGoods.setShipPrice(goodsCartListData.getGoodsShipPrice());
            cartGoods.setGoodsPrice(goodsCartListData.getGoodsPrice());
            List<CartGoodsNew> newList = new ArrayList<>();
            for (GoodsVoListData data : goodsCartListData.getGoodsLists()){
                CartGoodsNew cartGoodsNew = new CartGoodsNew();
                cartGoodsNew.setId(data.getGoodsId()+"");
                cartGoodsNew.setIcon(data.getGoodsPhotoPath());
                cartGoodsNew.setTitle(data.getGoodsName());
                long timeDiff = TimeUtils.getTimeDifference(selectBalanceAllData.getNowDate(),data.getFightGoodsEndTime());
                if(timeDiff>0){
                    cartGoodsNew.setTogetherEndMillis(timeDiff);
                }else{
                    cartGoodsNew.setTogetherEndMillis(0);
                }

                cartGoodsNew.setGoodsType(data.getGoodsType()+"");

                List<CartGoodsStyle> cartGoodsStyleList = new ArrayList<>();
                for(GoodsGspData goodsGspListData:data.getGoodsGsp()){
                    CartGoodsStyle cartGoodsStyle = new CartGoodsStyle();
                    cartGoodsStyle.setId(goodsGspListData.getCartGsp());
                    cartGoodsStyle.setCartId(goodsGspListData.getGoodsCartId()+"");
                    cartGoodsStyle.setMainStyle(goodsGspListData.getSpecColor());
                    cartGoodsStyle.setSecondStyle(goodsGspListData.getSpecSize());
                    cartGoodsStyle.setPrice(goodsGspListData.getNowPrice());
                    cartGoodsStyle.setBuyNum(goodsGspListData.getCount());

                    cartGoodsStyle.setGoodsGsp(goodsGspListData.getCartGsp());
                    cartGoodsStyle.setGoodsSpecName(goodsGspListData.getGoodsSpecName());
                    cartGoodsStyle.setGoodsSpecContent(goodsGspListData.getGoodsSpecContent());

//                    if("0".equals(goodsCartListData.getGoodsLists().get(0).getGoodsNumType())){
//                        cartGoodsStyle.setType(CartGoodsStyle.GOODS_TYPE.FUTURES);
//                    }else if("1".equals(goodsCartListData.getGoodsLists().get(0).getGoodsNumType())){
//                        cartGoodsStyle.setType(CartGoodsStyle.GOODS_TYPE.STOCK);
//                    }

                    cartGoodsStyleList.add(cartGoodsStyle);
                }

                //阶梯价格
                List<LevelPrice> levelPrices = new ArrayList<>();
                String tierdPrice = data.getTierdPrice();
                try {
                    JSONArray jsonArray = new JSONArray(tierdPrice);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                        LevelPrice levelPrice = new LevelPrice();
                        String count = jsonObject.getString("count");
                        String price = jsonObject.getString("price");
                        levelPrice.setMinNum(NumberUtils.getIntFromString(count.split("-")[0]));
                        levelPrice.setMaxNum(NumberUtils.getIntFromString(count.split("-")[1]));
                        levelPrice.setPrice(NumberUtils.getDoubleFromString(price));
                        levelPrices.add(levelPrice);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cartGoodsNew.setLevelPrices(levelPrices);
                cartGoodsNew.setGoodsStyles(cartGoodsStyleList);

                newList.add(cartGoodsNew);
            }

            cartGoods.setCartGoodsList(newList);

            cartGoodsList.add(cartGoods);
        }

        return cartGoodsList;
    }

    private List<CartGoods> mappedList(SelectBalanceAllData selectBalanceAllData){
        List<CartGoods> cartGoodsList = new ArrayList<>();
        for(GoodsListsData goodsCartListData:selectBalanceAllData.getGoodsVoList()){
            CartGoods cartGoods = new CartGoods();
            cartGoods.setId(goodsCartListData.getGoodsLists().get(0).getGoodsId()+"");
            cartGoods.setIcon(goodsCartListData.getGoodsLists().get(0).getGoodsPhotoPath());
            cartGoods.setTitle(goodsCartListData.getGoodsLists().get(0).getGoodsName());
            cartGoods.setStoreId(goodsCartListData.getStoreId()+"");
            long timeDiff = TimeUtils.getTimeDifference(selectBalanceAllData.getNowDate(),goodsCartListData.getGoodsLists().get(0).getFightGoodsEndTime());
            if(timeDiff>0){
                cartGoods.setTogetherEndMillis(timeDiff);
            }else{
                cartGoods.setTogetherEndMillis(0);
            }
            List<CartGoodsStyle> cartGoodsStyleList = new ArrayList<>();
            for(GoodsGspData goodsGspListData:goodsCartListData.getGoodsLists().get(0).getGoodsGsp()){
                CartGoodsStyle cartGoodsStyle = new CartGoodsStyle();
                cartGoodsStyle.setId(goodsGspListData.getCartGsp());
                cartGoodsStyle.setCartId(goodsGspListData.getGoodsCartId()+"");
                cartGoodsStyle.setMainStyle(goodsGspListData.getSpecColor());
                cartGoodsStyle.setSecondStyle(goodsGspListData.getSpecSize());
                cartGoodsStyle.setPrice(goodsGspListData.getNowPrice());
                cartGoodsStyle.setBuyNum(goodsGspListData.getCount());

                cartGoodsStyle.setGoodsGsp(goodsGspListData.getCartGsp());
                cartGoodsStyle.setGoodsSpecName(goodsGspListData.getGoodsSpecName());
                cartGoodsStyle.setGoodsSpecContent(goodsGspListData.getGoodsSpecContent());

                if("0".equals(goodsCartListData.getGoodsLists().get(0).getGoodsNumType())){
                    cartGoodsStyle.setType(CartGoodsStyle.GOODS_TYPE.FUTURES);
                }else if("1".equals(goodsCartListData.getGoodsLists().get(0).getGoodsNumType())){
                    cartGoodsStyle.setType(CartGoodsStyle.GOODS_TYPE.STOCK);
                }

                cartGoodsStyleList.add(cartGoodsStyle);
            }
            cartGoods.setGoodsStyles(cartGoodsStyleList);
            cartGoods.setGoodsType(goodsCartListData.getGoodsLists().get(0).getGoodsType()+"");

            //阶梯价格
            List<LevelPrice> levelPrices = new ArrayList<>();
            String tierdPrice = goodsCartListData.getGoodsLists().get(0).getTierdPrice();
            try {
                JSONArray jsonArray = new JSONArray(tierdPrice);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                    LevelPrice levelPrice = new LevelPrice();
                    String count = jsonObject.getString("count");
                    String price = jsonObject.getString("price");
                    levelPrice.setMinNum(NumberUtils.getIntFromString(count.split("-")[0]));
                    levelPrice.setMaxNum(NumberUtils.getIntFromString(count.split("-")[1]));
                    levelPrice.setPrice(NumberUtils.getDoubleFromString(price));
                    levelPrices.add(levelPrice);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            cartGoods.setLevelPrices(levelPrices);

            cartGoodsList.add(cartGoods);
        }

        return cartGoodsList;
    }

    private Address mappedAddress(ReceiverAddrVoData receiverAddrVo){
        Address address = new Address();
        address.setId(receiverAddrVo.getId()+"");
        address.setName(receiverAddrVo.getTrueName());
        address.setMobile(receiverAddrVo.getMobile());
        address.setArea(receiverAddrVo.getProvinceName()+receiverAddrVo.getCityName()+receiverAddrVo.getAreaName());
        address.setDetailAddress(receiverAddrVo.getArea_info());
        return address;
    }


}
