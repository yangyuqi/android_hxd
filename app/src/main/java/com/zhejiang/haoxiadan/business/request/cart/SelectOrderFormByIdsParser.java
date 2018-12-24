package com.zhejiang.haoxiadan.business.request.cart;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Address;
import com.zhejiang.haoxiadan.model.common.Order;
import com.zhejiang.haoxiadan.model.common.OrderGoods;
import com.zhejiang.haoxiadan.model.common.Supplier;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsGspData2;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsInfoListData;
import com.zhejiang.haoxiadan.model.requestData.in.OrderFormListData2;
import com.zhejiang.haoxiadan.model.requestData.in.SelectOrderFormByIdsData;
import com.zhejiang.haoxiadan.model.requestData.out.DeliveryMessageBean;
import com.zhejiang.haoxiadan.util.NumberUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据订单id，订单详情
 * Created by KK on 2017/9/5.
 */
public class SelectOrderFormByIdsParser extends AbsBaseParser {

    private String damm ;

    public SelectOrderFormByIdsParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        SelectOrderFormByIdsData selectOrderFormByIdsData = mDataParser.parseObject(data,SelectOrderFormByIdsData.class);
        Order order = mapped(selectOrderFormByIdsData);
        SelectOrderFormByIdsListener listener = (SelectOrderFormByIdsListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onSelectOrderFormByIdsSuccess(order);
        }
    }

    private Order mapped(SelectOrderFormByIdsData selectOrderFormByIdsData){
        OrderFormListData2 orderFormListData = selectOrderFormByIdsData.getOrderFormList().get(0);

        Order order = new Order();
        ArrayList<OrderGoods> orderGoodsList = new ArrayList<>();
        for(GoodsInfoListData goodsInfoListData:orderFormListData.getGoodsInfoList()){
            for (GoodsGspData2 goodsGspData2:goodsInfoListData.getGoodsGsp()){
                OrderGoods orderGoods = new OrderGoods();
                orderGoods.setId(goodsInfoListData.getGoods_id()+"");
                orderGoods.setIcon(goodsInfoListData.getGoods_mainphoto_path());
                orderGoods.setTitle(goodsInfoListData.getGoods_name());
                orderGoods.setPrice(goodsGspData2.getGoodsSinglePrice()+"");
                String style = "";
                for(int i=0;i<goodsGspData2.getGoodsSpecName().size();i++){
                    style += goodsGspData2.getGoodsSpecName().get(i)+":"+goodsGspData2.getGoodsSpecContent().get(i)+"    ";
                }
                orderGoods.setStyle(style);
                orderGoods.setCount(goodsInfoListData.getGoodsTotalcount()+"");
                orderGoods.setPer_count(""+goodsGspData2.getCount());
                if("0".equals(goodsInfoListData.getGoodsNumType())){
                    orderGoods.setType(OrderGoods.GOODS_TYPE.FUTURES);
                }else if("1".equals(goodsInfoListData.getGoodsNumType())){
                    orderGoods.setType(OrderGoods.GOODS_TYPE.STOCK);
                }

                orderGoods.setOrderStatus(orderFormListData.getOrderStatus());
                orderGoods.setGoodsCartId(goodsGspData2.getGoodsCartId());

                if (goodsGspData2.getDelivery()!=null){
                    ArrayList<DeliveryMessageBean> arrayList = new ArrayList<>();
                    JsonParser parser = new JsonParser();
                    //将JSON的String 转成一个JsonArray对象
                    Gson gson = new Gson();
                    JsonArray jsonArray = parser.parse(gson.toJson(goodsGspData2.getDelivery())).getAsJsonArray();
                    for (JsonElement jsonElement : jsonArray) {
                        //使用GSON，直接转成Bean对象
                        DeliveryMessageBean userBean = gson.fromJson(jsonElement, DeliveryMessageBean.class);
                        arrayList.add(userBean);
                    }
                    orderGoods.setDelivery(arrayList);
                }

                if (goodsGspData2.getRefundStatus()!=null){
                    if (goodsGspData2.getRefundStatus()==3){
                        orderGoods.setRefundStatus("已拒绝");
                    }else if (goodsGspData2.getRefundStatus()==1){
                        orderGoods.setRefundStatus("退款中");
                    }else if (goodsGspData2.getRefundStatus()==2){
                        orderGoods.setRefundStatus("退款成功");
                    }else if (goodsGspData2.getRefundStatus()==0){
                        orderGoods.setRefundStatus("退款审核中");
                    }
                }else {
                    orderGoods.setRefundStatus(null);
                }

                if (goodsGspData2.getRefundType()!=null){
                    orderGoods.setRefundType(""+goodsGspData2.getRefundType());
                }else {
                    orderGoods.setRefundType(null);
                }

                orderGoodsList.add(orderGoods);
            }
        }
        Supplier supplier = new Supplier();
        supplier.setId(orderFormListData.getStoreId());
        supplier.setCompany(orderFormListData.getStoreName());
        order.setMainId(selectOrderFormByIdsData.getOrderformMainId()+"");
        order.setMainOrderNo(selectOrderFormByIdsData.getMainOrder_id());
        order.setSupplier(supplier);
        switch (orderFormListData.getOrderStatus()){
            case "10":
                //待付款
                order.setOrderStatus(Order.ORDER_STATUS.WAIT_PAY);
                break;
            case "20":
                if(orderFormListData.getRefundApplyFormList() != null && orderFormListData.getRefundApplyFormList().size()>0){//有申请退款记录
                    if(orderFormListData.getRefundApplyFormList().size() == 1){
                        //商家一次不同意
                        order.setOrderStatus(Order.ORDER_STATUS.SALER_REFUSE_ONE);
                        order.setRefundReason(orderFormListData.getRefundApplyFormList().get(0).getAuditFailReason());
                    }else{
                        //商家二次不同意
                        order.setOrderStatus(Order.ORDER_STATUS.SALER_REFUSE_TWO);
                        order.setRefundReason(orderFormListData.getRefundApplyFormList().get(0).getAuditFailReason());
                    }
                }else{
                    //待发货
                    order.setOrderStatus(Order.ORDER_STATUS.WAIT_SHIP);
                }
                break;
            case "30":
                //待收货
                order.setOrderStatus(Order.ORDER_STATUS.WAIT_RECEIVE);
                break;
            case "40":
                //待评价
                order.setOrderStatus(Order.ORDER_STATUS.WAIT_EVALUATE);
                break;
            case "50":
                //已完成
                order.setOrderStatus(Order.ORDER_STATUS.COMPLETE);
                break;
            case "0":
                if(orderFormListData.getIsAlready() == 0){
                    //未成团
                    order.setOrderStatus(Order.ORDER_STATUS.END);
                }else{
                    //已取消
                    order.setOrderStatus(Order.ORDER_STATUS.CANCEL);
                }
                break;
            case "21":
                //已申请退款
                order.setOrderStatus(Order.ORDER_STATUS.APPLY_REFUND);
                break;
            case "22":
                //退款中
                order.setOrderStatus(Order.ORDER_STATUS.REFUND_ING);
                break;
            case "25":
                //退款完成
                order.setOrderStatus(Order.ORDER_STATUS.REFUND_COMPLETE);
                break;
        }
        order.setOrderNo(orderFormListData.getOrderId());
        order.setAddTime(orderFormListData.getAddtime());
        order.setMessage(orderFormListData.getMsg());
        order.setGoodsCount(selectOrderFormByIdsData.getTotalAllCount());
        if(orderFormListData.getIsChangePrice() == 1){
            order.setTotalPrice(NumberUtils.getDoubleFromString(orderFormListData.getRevisedPrice()+""));
        }else{
            order.setTotalPrice(NumberUtils.getDoubleFromString(orderFormListData.getTotalPrice()+""));
        }
        order.setShipPrice(NumberUtils.getDoubleFromString(selectOrderFormByIdsData.getTotalAllshipPrice()));
        order.setTotalGoodsPrice(NumberUtils.getDoubleFromString(selectOrderFormByIdsData.getGoodsAmountAll()));
        order.setGoodsList(orderGoodsList);
        Address address = new Address();
        address.setId(orderFormListData.getReceiverAddrId()+"");
        address.setName(orderFormListData.getReceiverName());
        address.setMobile(orderFormListData.getReceiverMobile());
        address.setArea(orderFormListData.getReceiverArea());
        address.setDetailAddress(orderFormListData.getReceiverAreaInfo());
        order.setAddress(address);
        order.setShipNo(orderFormListData.getShipCode());
        if (orderFormListData.getShipTime()!=null) {
            order.setShipTime(orderFormListData.getShipTime());
        }
        order.setPayTime(orderFormListData.getPayTime());
        order.setPayType(orderFormListData.getPaymentName());
        order.setOrderExplain(orderFormListData.getOrderExplain());
        order.setOrderformMainId(orderFormListData.getOrderformMainId());
        return order;
    }

}
