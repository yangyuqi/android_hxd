package com.zhejiang.haoxiadan.business.request.my;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.zhejiang.haoxiadan.business.BaseRequester;
import com.zhejiang.haoxiadan.business.request.cart.AddEvaluateListener;
import com.zhejiang.haoxiadan.business.request.cart.AddEvaluateParser;
import com.zhejiang.haoxiadan.business.request.cart.AddOrderFormListener;
import com.zhejiang.haoxiadan.business.request.cart.AddOrderFormParser;
import com.zhejiang.haoxiadan.business.request.cart.ApplyRefundOrderListener;
import com.zhejiang.haoxiadan.business.request.cart.ApplyRefundOrderParser;
import com.zhejiang.haoxiadan.business.request.cart.CancelOrderListener;
import com.zhejiang.haoxiadan.business.request.cart.CancelOrderParser;
import com.zhejiang.haoxiadan.business.request.cart.DeleteOrderFormListener;
import com.zhejiang.haoxiadan.business.request.cart.DeleteOrderFormParser;
import com.zhejiang.haoxiadan.business.request.cart.GetCancelOrderReasonListener;
import com.zhejiang.haoxiadan.business.request.cart.GetCancelOrderReasonParser;
import com.zhejiang.haoxiadan.business.request.cart.GetExpressListener;
import com.zhejiang.haoxiadan.business.request.cart.GetExpressParser;
import com.zhejiang.haoxiadan.business.request.cart.GetRefundOrderReasonListener;
import com.zhejiang.haoxiadan.business.request.cart.GetRefundOrderReasonParser;
import com.zhejiang.haoxiadan.business.request.cart.OrderConfirmDelayListener;
import com.zhejiang.haoxiadan.business.request.cart.OrderConfirmDelayParser;
import com.zhejiang.haoxiadan.business.request.cart.OrderSureReceiveListener;
import com.zhejiang.haoxiadan.business.request.cart.OrderSureReceiveParser;
import com.zhejiang.haoxiadan.business.request.cart.RefundDetailListener;
import com.zhejiang.haoxiadan.business.request.cart.RefundDetailParser;
import com.zhejiang.haoxiadan.business.request.cart.RemindDeliverListener;
import com.zhejiang.haoxiadan.business.request.cart.RemindDeliverParser;
import com.zhejiang.haoxiadan.business.request.cart.SelectOrderFormAllListener;
import com.zhejiang.haoxiadan.business.request.cart.SelectOrderFormAllParser;
import com.zhejiang.haoxiadan.business.request.cart.SelectOrderFormByIdsListener;
import com.zhejiang.haoxiadan.business.request.cart.SelectOrderFormByIdsParser;
import com.zhejiang.haoxiadan.business.request.cart.SelectOrderFormCountListener;
import com.zhejiang.haoxiadan.business.request.cart.SelectOrderFormCountParser;
import com.zhejiang.haoxiadan.business.request.pay.EditOfFinaListener;
import com.zhejiang.haoxiadan.business.request.pay.EditOfFinaParser;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.common.CartGoods;
import com.zhejiang.haoxiadan.model.common.CartGoodsStyle;
import com.zhejiang.haoxiadan.model.common.Order;
import com.zhejiang.haoxiadan.model.requestData.in.CartGoodsNew;
import com.zhejiang.haoxiadan.third.network.Server;
import com.zhejiang.haoxiadan.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KK on 2017/9/5.
 */
public class OrderRequester extends BaseRequester {


    /**
     * 新增订单
     * @param context
     * @param accessToken
     * @param addressId
     * @param cartGoodses
     * @param listener
     */
    public void addOrderForm(Context context, String accessToken, String addressId, List<CartGoods> cartGoodses, AddOrderFormListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        params.put("orderType","android");
        params.put("payType","online");
        params.put("receiverAddrId",addressId);
        List<String> stringList = new ArrayList<>();
        List<Map<String,Object>> storeVoList = new ArrayList<>();
        for(CartGoods cartGoods : cartGoodses){
            Map<String,Object> new_goodsVo = new HashMap<>();
            new_goodsVo.put("storeId",cartGoods.getStoreId());
            if (cartGoods.getMessage()==null) {
                new_goodsVo.put("msg", "");
            }else {
                new_goodsVo.put("msg", cartGoods.getMessage());
            }
            List<Map<String,Object>> goodsList = new ArrayList<>();
            for (CartGoodsNew goodsNew : cartGoods.getCartGoodsList()) {
                Map<String, Object> goodsVo = new HashMap<>();
//                goodsVo.put("storeId", cartGoods.getStoreId());
                int s = (int) Double.parseDouble(goodsNew.getId());
                goodsVo.put("goodsId", ""+s);
//                goodsVo.put("msg", cartGoods.getMessage());
//                goodsVo.put("goodsName", cartGoods.getTitle());
//                goodsVo.put("shipPrice", cartGoods.getShipPrice());
                List<Map<String, Object>> goodsGspList = new ArrayList<>();
                for (CartGoodsStyle cartGoodsStyle : goodsNew.getGoodsStyles()) {
                    if (cartGoodsStyle.getCartId() != null) {
                        stringList.add(cartGoodsStyle.getCartId());
                    }
                    Map<String, Object> goodsGsp = new HashMap<>();
                    goodsGsp.put("cartGsp", cartGoodsStyle.getGoodsGsp());
                    goodsGsp.put("count", cartGoodsStyle.getBuyNum());
                    goodsGsp.put("goodsSpecName", cartGoodsStyle.getGoodsSpecName());
                    goodsGsp.put("goodsSpecContent", cartGoodsStyle.getGoodsSpecContent());
                    goodsGspList.add(goodsGsp);
                }
                goodsVo.put("goodsGsp", goodsGspList);
                 goodsList.add(goodsVo);
//                goodsVoList.add(goodsVo);
            }
            new_goodsVo.put("goodsList",goodsList);

            storeVoList.add(new_goodsVo);
        }
        if(stringList.size()>0){
            params.put("goodscartId",stringList);
        }
//        params.put("goodsVoList",goodsVoList);
        params.put("storeVoList",storeVoList);
        post(context, Server.getUrl("orderForm/addOrderForm"),params,listener,new AddOrderFormParser(listener));
    }

    /**
     * 根据用户id获取所有订单信息
     * @param context
     * @param accessToken
     * @param status
     * @param pageNo
     * @param pageSize
     * @param listener
     */
    public void selectOrderFormAll(Context context, String accessToken, Order.ORDER_STATUS status,int pageNo,int pageSize, SelectOrderFormAllListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        params.put("currentPage",pageNo);
        params.put("showCount",pageSize);
        switch (status){
            case ALL:
                break;
            case WAIT_PAY:
                params.put("orderStatus",10);
                break;
            case WAIT_SHIP:
                params.put("orderStatus",20);
                break;
            case WAIT_RECEIVE:
                params.put("orderStatus",30);
                break;
            case WAIT_EVALUATE:
                params.put("orderStatus",40);
                break;
            case AFTER_SALES:
                String[] statusList = new String[]{"21","22","25"};
                params.put("orderStatuslist",statusList);
                break;
        }
        post(context, Server.getUrl("orderForm/selectOrderFormAll"),params,listener,new SelectOrderFormAllParser(listener));
    }

    /**
     * 根据订单id，订单详情
     * @param context
     * @param accessToken
     * @param id
     * @param listener
     */
    public void selectOrderFormByIds(Context context, String accessToken,String id, SelectOrderFormByIdsListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        params.put("ids",id);
        post(context, Server.getUrl("orderForm/selectOrderFormByIds"),params,listener,new SelectOrderFormByIdsParser(listener));
    }

    /**
     * 订单物流信息接口
     * @param context
     * @param shipNo
     * @param orderId
     * @param listener
     */
    public void getExpress(Context context, String shipNo,String orderId, GetExpressListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("shipCode",shipNo);
        params.put("orderId",orderId);
        post(context, Server.getUrl("order/getExpress"),params,listener,new GetExpressParser(listener));
    }

    /**
     * 提醒卖家发货
     * @param context
     * @param orderId
     * @param listener
     */
    public void remindDeliver(Context context,String orderId, RemindDeliverListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken", SharedPreferencesUtil.get(context, Constants.accessToken,""));
        params.put("orderFormId",orderId);
        post(context, Server.getUrl("remind/remindDeliver"),params,listener,new RemindDeliverParser(listener));
    }

    /**
     * 延时收货
     * @param context
     * @param orderId
     * @param listener
     */
    public void orderConfirmDelay(Context context,String orderId, OrderConfirmDelayListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken", SharedPreferencesUtil.get(context, Constants.accessToken,""));
        params.put("orderFormId",orderId);
        post(context, Server.getUrl("delay/orderConfirmDelay"),params,listener,new OrderConfirmDelayParser(listener));
    }

    /**
     * 确认收货
     * @param context
     * @param orderId
     * @param listener
     */
    public void orderSureReceive(Context context,String orderId, OrderSureReceiveListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken", SharedPreferencesUtil.get(context, Constants.accessToken,""));
        params.put("id",orderId);
        params.put("orderStatus",40);//40确认收货
        post(context, Server.getUrl("orderForm/editOrderFormOrderStatus"),params,listener,new OrderSureReceiveParser(listener));
    }

    public void orderSureReceive2(Context context,String orderId,String orderNo , OrderSureReceiveListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken", SharedPreferencesUtil.get(context, Constants.accessToken,""));
        params.put("id",orderId);
        params.put("orderStatus",29);//40确认收货
        params.put("orderNo",orderNo);
        post(context, Server.getUrl("orderForm/editOrderFormOrderStatus"),params,listener,new OrderSureReceiveParser(listener));
    }

    /**
     * 删除订单
     * @param context
     * @param orderId
     * @param listener
     */
    public void deleteOrderForm(Context context,String orderId, DeleteOrderFormListener listener){
        Map<String, Object> params = new HashMap<>();
        String[] ids = new String[]{orderId};
        params.put("ids",ids);
        post(context, Server.getUrl("orderForm/deleteOrderForm"),params,listener,new DeleteOrderFormParser(listener));
    }

    /**
     * 取消订单原因
     * @param context
     * @param listener
     */
    public void getCancelOrderReason(Context context, GetCancelOrderReasonListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("typeId","2");
        post(context, Server.getUrl("dictionaryData/selectdictionaryDataByTypeId"),params,listener,new GetCancelOrderReasonParser(listener));
    }

    /**
     * 取消订单
     * @param context
     * @param orderId
     * @param listener
     */
    public void cancelOrder(Context context,String orderId,String reason, CancelOrderListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken", SharedPreferencesUtil.get(context, Constants.accessToken,""));
        params.put("orderFormId",orderId);
        params.put("userApplyReason",reason);
        params.put("refundType",2);//2取消订单
        post(context, Server.getUrl("orderForm/refundOrderFormById"),params,listener,new CancelOrderParser(listener));
    }


    /**
     * 获得退款原因
     * @param context
     * @param listener
     */
    public void getRefundOrderReason(Context context, GetRefundOrderReasonListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("typeId","1");
        post(context, Server.getUrl("dictionaryData/selectdictionaryDataByTypeId"),params,listener,new GetRefundOrderReasonParser(listener));
    }

    /**
     * 申请订单退款
     * @param context
     * @param orderId
     * @param reason
     * @param imgs
     * @param listener
     */
    public void applyRefund(Context context,String orderId,String reason,String msg,List<String> imgs, int type , String refund ,String goodsCartId ,ApplyRefundOrderListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken", SharedPreferencesUtil.get(context, Constants.accessToken,""));
        params.put("orderFormId",orderId);
        params.put("goodsCartId",goodsCartId);
        params.put("userApplyReason",reason);
        params.put("userApplyExplain",msg);
        params.put("userApplyImg",imgs);
        params.put("refundType",1);//2取消订单
        params.put("type",type);
        params.put("refund",refund);
        post(context, Server.getUrl("orderForm/refundOrderFormById"),params,listener,new ApplyRefundOrderParser(listener));
    }

    public void applyRefundChange(Context context,String orderId,String reason,String msg,List<String> imgs, int type , String refund ,String goodsCartId ,ApplyRefundOrderListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken", SharedPreferencesUtil.get(context, Constants.accessToken,""));
        params.put("orderId",orderId);
        params.put("goodsCartId",goodsCartId);
        params.put("userApplyReason",reason);
        params.put("userApplyExplain",msg);
        params.put("userApplyImg",imgs);
//        params.put("refundType",1);//2取消订单
        params.put("type",type);
        params.put("refund",refund);
        post(context, Server.getUrl1("orderForm/returnOrderFormById"),params,listener,new ApplyRefundOrderParser(listener));
    }

    public void applyRefundexchange(Context context,String orderId,String reason,String msg,List<String> imgs, int type , String refund ,String goodsCartId ,ApplyRefundOrderListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken", SharedPreferencesUtil.get(context, Constants.accessToken,""));
        params.put("orderId",orderId);
        params.put("goodsCartId",goodsCartId);
        params.put("userApplyReason",reason);
        params.put("userApplyExplain",msg);
        params.put("userApplyImg",imgs);
//        params.put("refundType",1);//2取消订单
        params.put("type",type);
//        params.put("refund",refund);
        post(context, Server.getUrl3("orderForm/exchangeOrderFormById"),params,listener,new ApplyRefundOrderParser(listener));
    }

    /**
     * 根据订单id，查询退款状态
     * @param context
     * @param orderId
     * @param listener
     */
    public void refundDetail(Context context,String orderId, RefundDetailListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken", SharedPreferencesUtil.get(context, Constants.accessToken,""));
        params.put("orderformId",orderId);
        params.put("refundType","1");//1为退款的数据
        post(context, Server.getUrl("orderForm/refundOrderFormStatusById"),params,listener,new RefundDetailParser(listener));
    }


    public void refundNewDetail(Context context,String orderId, String goodsCartId ,RefundNewDetailsListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken", SharedPreferencesUtil.get(context, Constants.accessToken,""));
        params.put("id",orderId);
        params.put("goodsCartId",goodsCartId);//1为退款的数据
        post(context, Server.getUrl3("orderForm/getOrderFormGoodsInfoById"),params,listener,new RefundNewDetailsParser(listener));
    }

    /**
     * 新增订单评论
     * @param context
     * @param orderId
     * @param goodsId
     * @param description 描述相符评价
     * @param service 服务态度评价
     * @param ship 发货速度评价
     * @param evaluate 评价内容
     * @param imgs 晒单图片
     * @param isAnonymous 是否匿名
     * @param listener
     */
    public void addEvaluate(Context context,String orderId,String goodsId,int description,int service,int ship,String evaluate,List<String> imgs,boolean isAnonymous, AddEvaluateListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken", SharedPreferencesUtil.get(context, Constants.accessToken,""));
        params.put("orderformId",orderId);
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> evaluGoodsInfo = new HashMap<>();
        evaluGoodsInfo.put("evaluateGoodsId",goodsId);
        evaluGoodsInfo.put("descriptionEvaluate",description+"");
        evaluGoodsInfo.put("serviceEvaluate",service+"");
        evaluGoodsInfo.put("shipEvaluate",ship+"");
        evaluGoodsInfo.put("evaluateInfo",evaluate);
        evaluGoodsInfo.put("evaluatePhotos",imgs);
        if(isAnonymous){
            evaluGoodsInfo.put("isAnonymous","1");
        }else{
            evaluGoodsInfo.put("isAnonymous","0");
        }
        list.add(evaluGoodsInfo);
        params.put("evaluGoodsInfo",list);
        post(context, Server.getUrl("evaluate/addEvaluate"),params,listener,new AddEvaluateParser(listener));
    }


    /**
     * 查询各订单状态下的订单数量
     * @param context
     * @param accessToken
     * @param listener
     */
    public void selectOrderFormCount(Context context,String accessToken, SelectOrderFormCountListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken", accessToken);
        post(context, Server.getUrl("orderForm/selectOrderFormCount"),params,listener,new SelectOrderFormCountParser(listener));
    }


    /**
     * 订单搜索
     * @param context
     * @param accessToken
     * @param key
     * @param pageNo
     * @param pageSize
     * @param listener
     */
    public void searchOrderFormAll(Context context, String accessToken,String key,int pageNo,int pageSize, SelectOrderFormAllListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        params.put("vagueSearchVal",key);
        params.put("currentPage",pageNo);
        params.put("showCount",pageSize);
        post(context, Server.getUrl("orderForm/selectOrderFormAll"),params,listener,new SelectOrderFormAllParser(listener));
    }

    public void userIsFinanceRegister(Context context ,String user_phone ,UserIsFinanceRegisterListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("user_phone",user_phone);
        post(context,Server.getFinanceUrl("/system/v1/isExist"),map,listener,new UserIsFinanceRegisterParser(listener));
    }

    public void goRegisterFinanceUser(Context context ,String user_phone ,String user_name ,GoRegisterFinanceUserListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("user_phone",user_phone);
        map.put("User_name",user_name);
        map.put("form","haoxiadan");
        map.put("type","auto");
        post(context,Server.getFinanceUrl("/system/v1/regist"),map,listener,new GoRegisterFinanceUserParser(listener));
    }

    public void loanApply(Context context ,String order_id ,String loan_mortgage ,String user_phone ,String user_name ,String from ,String order_amount ,int loan_term ,String institution_id ,String institution_name ,LoanApplyListener loanApplyListener){
        Map<String,Object> map = new HashMap<>();
        map.put("id",order_id);
        map.put("loan_mortgage",loan_mortgage);
        map.put("user_phone",user_phone);
        map.put("user_name",user_name);
        map.put("from",from);
        map.put("order_amount",order_amount);
        map.put("loan_term",loan_term);
        map.put("institution_id",institution_id);
        map.put("institution_name",institution_name);
        post(context,Server.getFinanceUrl("/loanManage/v1/loanApply"),map,loanApplyListener,new LoanApplyParser(loanApplyListener));
    }

    /**
     * 获取贷款机构
     * @param context
     * @param listener
     */
    public void getAllInstitutionNames(Context context ,GetAllInstitutionNamesListener listener){
        get(context,Server.getFinanceUrl("/institution/v1/getAllInstitutionNames"),listener,new GetAllInstitutionNamesParser(listener));
    }

    public void getFinanceText(Context context ,GetFinanceTextListener listener){
        get(context,Server.getFinanceUrl("/article/v1/getArticle?type=attention"),listener,new GetFinanceTextParser(listener));
    }

    public void editOfFinal(Context context , String accessToken ,String orderId , EditOfFinaListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("id",orderId);
        post(context,Server.getUrl("orderForm/editOfFina"),map,listener,new EditOfFinaParser(listener));

    }
}
