package com.zhejiang.haoxiadan.business.request.chat;

import android.content.Context;

import com.zhejiang.haoxiadan.business.BaseRequester;
import com.zhejiang.haoxiadan.third.network.Server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wqd on 2017/9/6 0006.
 */

public class ChatRequester extends BaseRequester {

    /**
     * 根据店铺id获取客服信息
     * @param context
     * @param storeId
     * @param queryCustomerByStoreIdListener
     */
    public void queryCustomerByStoreId(Context context , String storeId,String mobile, QueryCustomerByStoreIdListener queryCustomerByStoreIdListener){
        Map<String ,Object> map = new HashMap<>();
        map.put("storeId",storeId);
        map.put("mobile",mobile);
        post(context, Server.getUrl("store/queryCustomerByStoreId"),map,queryCustomerByStoreIdListener , new QueryCustomerByStoreIdParser(queryCustomerByStoreIdListener));
    }

    /***
     * 发送im消息
     * @param context
     * @param adminId
     * @param body
     * @param goodsId
     * @param storeId
     * @param sendMessageListener
     */
    public void sendMessage(Context context , String adminId,String body,String goodsId,String storeId,String buyName,String buyImg,String buyId,String storeName,String storeLogo,String customerPhone,String storePhone,
                            String userType,SendMessageListener sendMessageListener){
        Map<String ,Object> map = new HashMap<>();
        map.put("adminId",adminId);
        map.put("body",body);
        map.put("goodsId",goodsId);
        map.put("storeId",storeId);
        map.put("buyName",buyName);
        map.put("buyImg",buyImg);
        map.put("buyId",buyId);
        map.put("storeName",storeName);
        map.put("storeLogo",storeLogo);
        map.put("customerPhone",customerPhone);
        map.put("storePhone",storePhone);
        map.put("userType","BUYER");

        post(context, Server.getUrl("chat/sendMessage"),map,sendMessageListener , new SendMessageParser(sendMessageListener));
    }

    /****
     * 根据会话列表获取用户信息
     * @param context
     * @param adminId
     * @param lastMsgDate
     * @param queryUserListener
     */
    public void queryUser(Context context , String adminId,String lastMsgDate,String mobile, QueryUserListener queryUserListener){
        Map<String ,Object> map = new HashMap<>();
        map.put("adminId",adminId);
        map.put("lastMsgDate",lastMsgDate);
        map.put("mobile",mobile);
        post(context, Server.getUrl("chat/queryUser"),map,queryUserListener , new QueryUserParser(queryUserListener));
    }

    /***
     * 推送列表
     * @param context
     * @param access_token
     * @param currentPage
     * @param showCount
     * @param getPushInfoListener
     */
    public void getPushInfo(Context context , String access_token,String currentPage,String showCount, GetPushInfoListener getPushInfoListener){
        Map<String ,Object> map = new HashMap<>();
        map.put("access_token",access_token);
        map.put("currentPage",currentPage);
        map.put("showCount",showCount);
        post(context, Server.getUrl("user/getPushInfo"),map,getPushInfoListener , new GetPushInfoParser(getPushInfoListener));
    }

    /***
     * 获取订单消息
     * @param context
     * @param access_token
     * @param currentPage
     * @param showCount
     * @param orderFormAllForMessageListener
     */
    public void OrderFormAllForMessage(Context context , String access_token,String currentPage,String showCount, OrderFormAllForMessageListener orderFormAllForMessageListener){
        Map<String ,Object> map = new HashMap<>();
        map.put("accessToken",access_token);
        map.put("currentPage",currentPage);
        map.put("showCount",showCount);
        post(context, Server.getUrl("orderForm/OrderFormAllForMessage"),map,orderFormAllForMessageListener , new OrderFormAllForMessageParser(orderFormAllForMessageListener));
    }

    /***
     * 获取用户角色
     * @param context
     * @param access_token
     * @param getUserRoleListener
     */
    public void getUserRole(Context context , String access_token, GetUserRoleListener getUserRoleListener){
        Map<String ,Object> map = new HashMap<>();
        map.put("access_token",access_token);
        post(context, Server.getUrl("user/getUserRole"),map,getUserRoleListener , new GetUserRoleParser(getUserRoleListener));
    }


}
