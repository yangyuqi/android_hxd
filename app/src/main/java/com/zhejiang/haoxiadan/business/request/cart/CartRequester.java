package com.zhejiang.haoxiadan.business.request.cart;

import android.content.Context;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.BaseRequester;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.common.Cart;
import com.zhejiang.haoxiadan.model.common.CartGoods;
import com.zhejiang.haoxiadan.model.common.CartGoodsStyle;
import com.zhejiang.haoxiadan.model.common.GoodsStyle;
import com.zhejiang.haoxiadan.model.requestData.in.CartGoodsNew;
import com.zhejiang.haoxiadan.third.network.Server;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.NumberUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KK on 2017/8/23.
 */
public class CartRequester extends BaseRequester {

    /**
     * 根据用户id获取购物车商品信息
     * @param context
     * @param accessToken
     * @param pageNo
     * @param pageSize
     * @param selectGoodsCartAllListener
     */
    public void selectGoodsCartAll(Context context, String accessToken, String pageNo, String pageSize, String type ,SelectGoodsCartAllListener selectGoodsCartAllListener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        params.put("currentPage",pageNo);
        params.put("showCount",pageSize);
        params.put("goodsSort",type);
        post(context, Server.getUrl("goodsCart/selectGoodsCartAll"),params,selectGoodsCartAllListener,new SelectGoodsCartAllParser(selectGoodsCartAllListener));
    }

    /**
     * 删除购物车商品
     * @param context
     * @param accessToken
     * @param carts
     * @param listener
     */
    public void deleteGoodsCart(Context context, String accessToken, List<Cart> carts, DeleteGoodsCartListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);

        List<Integer> goodsCartIds = new ArrayList<>();
        for(Cart cart : carts){
            for(CartGoods cartGoods : cart.getCartGoodses()){
                for(CartGoodsStyle cartGoodsStyle : cartGoods.getGoodsStyles()){
                    if(cartGoodsStyle.isChoose()){
                        goodsCartIds.add(NumberUtils.getIntFromString(cartGoodsStyle.getCartId()));
                    }
                }

            }
        }
        if(goodsCartIds.size() == 0){
            ToastUtil.show(context, R.string.tip_select_goods);
            return;
        }
        params.put("goodsCartIds",goodsCartIds);

        post(context, Server.getUrl("goodsCart/deleteGoodsCart"),params,listener,new DeleteGoodsCartParser(listener));
    }


    /**
     * 修改购物车数量接口
     * @param context
     * @param accessToken
     * @param listener
     */
    public void editGoodsCartCount(Context context, String accessToken, String goodsId, String goodsCartId , String goodsType, int count, String cartGsp, String type ,EditGoodsCartCountListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String,Object> goodsCartList = new HashMap<>();
        goodsCartList.put("goodsId",goodsId);
        goodsCartList.put("goodsCartId",goodsCartId);
        goodsCartList.put("goodsType",goodsType);
        goodsCartList.put("count",count);
        goodsCartList.put("cartGsp",cartGsp);
        goodsCartList.put("goodsSort",type);
        list.add(goodsCartList);
        params.put("goodsCartList",list);

        post(context, Server.getUrl("goodsCart/editGoodsCartCount"),params,listener,new EditGoodsCartCountParser(listener));
    }

    /**
     * 购物车状态修改为2
     * @param context
     * @param accessToken
     * @param carts
     * @param listener
     */
    public void balanceStatus(Context context, String accessToken, List<Cart> carts, BalanceStatusListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);

        List<Integer> goodsCartIds = new ArrayList<>();
        for(Cart cart : carts){
            for(CartGoods cartGoods : cart.getCartGoodses()){
                for(CartGoodsStyle cartGoodsStyle : cartGoods.getGoodsStyles()){
                    if(cartGoodsStyle.isChoose()){
                        goodsCartIds.add(NumberUtils.getIntFromString(cartGoodsStyle.getCartId()));
                    }
                }

            }
        }
        params.put("goodsCartIds",goodsCartIds);

        post(context, Server.getUrl("goodsCart/balanceStatus"),params,listener,new BalanceStatusParser(listener));
    }

    /**
     * 查看修改购物车结算状态后数据列表
     * @param context
     * @param accessToken
     * @param addressId 首次可以不传，更改地址需要
     * @param listener
     */
    public void selectBalanceAll(Context context, String accessToken, String addressId, SelectBalanceAllListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        params.put("cartStatus","2");
        params.put("receiverAddrId",addressId);
        post(context, Server.getUrl("goodsCart/selectBalanceAll"),params,listener,new SelectBalanceAllParser(listener));
    }

    /**
     * 获取邮费接口
     * @param context
     * @param accessToken
     * @param addressId
     * @param cartGoodsList
     * @param listener
     */
    public void getshipPrice(Context context, String accessToken, String addressId,List<CartGoods> cartGoodsList, GetshipPriceListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        params.put("receiver_addr_id",addressId);
        List<Map<String,Object>> goodsList = new ArrayList<>();
        for(CartGoods cartGoods : cartGoodsList){
            Map<String,Object> goods = new HashMap<>();
            for (CartGoodsNew cartGoodsNew : cartGoods.getCartGoodsList()){
                goods.put("id",cartGoodsNew.getId());
                int count = 0;
                for(CartGoodsStyle cartGoodsStyle : cartGoodsNew.getGoodsStyles()){
                    count += cartGoodsStyle.getBuyNum();
                }
                goods.put("count",count);
            }
//            int count = 0;
//            for(CartGoodsStyle cartGoodsStyle : cartGoods.getGoodsStyles()){
//                count += cartGoodsStyle.getBuyNum();
//            }
//            Map<String,Object> goods = new HashMap<>();
//            goods.put("id",cartGoods.getId());
//            goods.put("count",count);
            goodsList.add(goods);
        }
        params.put("goodsList",goodsList);
        post(context, Server.getUrl("goodsCart/getshipPrice"),params,listener,new GetshipPriceParser(listener));
    }

    /**
     * 获得默认地址
     * @param context
     * @param accessToken
     * @param listener
     */
    public void getDefaultAddress(Context context ,String accessToken,GetDefaultAddressListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("currentPage",1);
        map.put("showCount",1);
        map.put("defaultVal",1);
        post(context,Server.getUrl("address/selectAddressAll"),map,listener,new GetDefaultAddressParser(listener));
    }
}
