package com.zhejiang.haoxiadan.business.request.cart;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Cart;
import com.zhejiang.haoxiadan.model.common.CartGoods;
import com.zhejiang.haoxiadan.model.common.CartGoodsStyle;
import com.zhejiang.haoxiadan.model.common.LevelPrice;
import com.zhejiang.haoxiadan.model.requestData.in.CartData;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsCartListData;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsGspListData;
import com.zhejiang.haoxiadan.model.requestData.in.StoreGoodsCartListData;
import com.zhejiang.haoxiadan.util.NumberUtils;
import com.zhejiang.haoxiadan.util.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KK on 2017/8/23.
 */
public class SelectGoodsCartAllParser  extends AbsBaseParser {

    public SelectGoodsCartAllParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        CartData cartData = mDataParser.parseObject(data,CartData.class);
        List<Cart> list = mapped(cartData);

        SelectGoodsCartAllListener listener = (SelectGoodsCartAllListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onSelectGoodsCartAllSuccess(list);
        }
    }

    //手动映射
    private List<Cart> mapped(CartData cartData){
        List<Cart> carts = new ArrayList<>();
        if(cartData.getStoreGoodsCartList() != null){

            for(StoreGoodsCartListData storeGoodsCartListData:cartData.getStoreGoodsCartList()){
                Cart cart = new Cart();
                cart.setSupplierId(storeGoodsCartListData.getStoreId()+"");
                cart.setSupplierName(storeGoodsCartListData.getStoreName());
                List<CartGoods> cartGoodsList = new ArrayList<>();
                for(GoodsCartListData goodsCartListData:storeGoodsCartListData.getGoodsCartList()){
                    CartGoods cartGoods = new CartGoods();
                    cartGoods.setId(goodsCartListData.getGoodsId()+"");
                    cartGoods.setIcon(goodsCartListData.getGoodsPhotoPath());
                    cartGoods.setTitle(goodsCartListData.getGoodsName());
                    if("1".equals(goodsCartListData.getGoodsType())){
                        long timeDiff = TimeUtils.getTimeDifference(cartData.getNowDate(),goodsCartListData.getFightGoodsEndTime());
                        if(timeDiff>0){
                            cartGoods.setTogetherEndMillis(timeDiff);
                        }else{
                            cartGoods.setTogetherEndMillis(0);
                        }
                    }
                    cartGoods.setGoodsNo(goodsCartListData.getGoodsSerial());
                    //阶梯价格
                    List<LevelPrice> levelPrices = new ArrayList<>();
                    String tierdPrice = goodsCartListData.getTierdPrice();
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
                    List<CartGoodsStyle> cartGoodsStyleList = new ArrayList<>();
                    for(GoodsGspListData goodsGspListData:goodsCartListData.getGoodsGspList()){
                        CartGoodsStyle cartGoodsStyle = new CartGoodsStyle();
                        cartGoodsStyle.setId(goodsGspListData.getCartGsp());
                        cartGoodsStyle.setCartId(goodsGspListData.getGoodsCartId()+"");
                        cartGoodsStyle.setMainStyle(goodsGspListData.getSpecColor());
                        cartGoodsStyle.setSecondStyle(goodsGspListData.getSpecSize());
                        cartGoodsStyle.setPrice(goodsGspListData.getNowPrice());
                        cartGoodsStyle.setBuyNum(goodsGspListData.getCount());
                        cartGoodsStyle.setMinBuyCount(goodsGspListData.getSpecpropertySmallCount());
                        cartGoodsStyle.setMaxBuyCount(goodsGspListData.getSpecpropertyInventCount());
                        switch (goodsCartListData.getGoodsNumType()){
                            case 0:
                                cartGoodsStyle.setType(CartGoodsStyle.GOODS_TYPE.FUTURES);
                                break;
                            case 1:
                                cartGoodsStyle.setType(CartGoodsStyle.GOODS_TYPE.STOCK);
                                break;
                        }

                        cartGoodsStyleList.add(cartGoodsStyle);
                    }
                    cartGoods.setGoodsStyles(cartGoodsStyleList);
                    cartGoods.setGoodsType(goodsCartListData.getGoodsType());
                    cartGoods.setGoodsLimit(goodsCartListData.getGoodsLimit());
                    cartGoodsList.add(cartGoods);
                }
                cart.setCartGoodses(cartGoodsList);

                carts.add(cart);
            }

        }
        return carts;
    }
}
