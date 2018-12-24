package com.zhejiang.haoxiadan.business.request.cart;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Order;
import com.zhejiang.haoxiadan.model.common.OrderGoods;
import com.zhejiang.haoxiadan.model.common.Supplier;
import com.zhejiang.haoxiadan.model.requestData.in.OrderFormListData;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsGspData2;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsInfoListData;
import com.zhejiang.haoxiadan.model.requestData.in.SelectOrderFormAllData;
import com.zhejiang.haoxiadan.model.requestData.out.DeliveryMessageBean;
import com.zhejiang.haoxiadan.util.NumberUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据用户id获取所有订单信息
 * Created by KK on 2017/9/5.
 */
public class SelectOrderFormAllParser extends AbsBaseParser {

    public SelectOrderFormAllParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        SelectOrderFormAllData selectOrderFormAllData = mDataParser.parseObject(data,SelectOrderFormAllData.class);
        List<Order> orders = mappedList(selectOrderFormAllData.getOrderFormList());

        SelectOrderFormAllListener listener = (SelectOrderFormAllListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onSelectOrderFormAllSuccess(orders);
        }
    }

    private List<Order> mappedList(List<OrderFormListData> orderFormListDatas){
        List<Order> orders = new ArrayList<>();
        for(OrderFormListData orderFormListData : orderFormListDatas){
            Order order = new Order();
            order.setId(orderFormListData.getId()+"");
            Supplier supplier = new Supplier();
            supplier.setId(orderFormListData.getStoreId());
            supplier.setCompany(orderFormListData.getStoreName());
            order.setSupplier(supplier);
            switch (orderFormListData.getOrderStatus()){
                case "10":
                    //待付款
                    order.setOrderStatus(Order.ORDER_STATUS.WAIT_PAY);
                    break;
                case "20":
                    if(orderFormListData.getRefundStatus() == 0){//不可退款了，是二次拒绝
                        //商家二次不同意
                        order.setOrderStatus(Order.ORDER_STATUS.SALER_REFUSE_TWO);
                    }else{
                        if(orderFormListData.getRefundApplyFormList() != null && orderFormListData.getRefundApplyFormList().size()>0){//有申请退款记录，是一次拒绝
                            //商家一次不同意
                            order.setOrderStatus(Order.ORDER_STATUS.SALER_REFUSE_ONE);
                            order.setRefundReason(orderFormListData.getRefundApplyFormList().get(0).getAuditFailReason());
                        }else{
                            //待发货
                            order.setOrderStatus(Order.ORDER_STATUS.WAIT_SHIP);
                        }
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
                default:
                    //已取消
                    order.setOrderStatus(Order.ORDER_STATUS.CANCEL);
                    break;

            }
            ArrayList<OrderGoods> orderGoodsList = new ArrayList<>();
            int goodsCount = 0;
            for(GoodsInfoListData goodsInfoListData: orderFormListData.getGoodsInfoList()){
                for(GoodsGspData2 goodsGspData:goodsInfoListData.getGoodsGsp()){
                    goodsCount += goodsGspData.getCount();
                    OrderGoods orderGoods = new OrderGoods();
                    orderGoods.setId(goodsInfoListData.getGoods_id()+"");
                    orderGoods.setIcon(goodsInfoListData.getGoods_mainphoto_path());
                    orderGoods.setTitle(goodsInfoListData.getGoods_name());
                    String style = "";
                    for(int i = 0;i<goodsGspData.getGoodsSpecName().size();i++){
                        style += goodsGspData.getGoodsSpecName().get(i)+":"+goodsGspData.getGoodsSpecContent().get(i)+"    ";
                    }
                    orderGoods.setStyle(style);
                    orderGoods.setPrice(goodsGspData.getGoodsPrice()+"");
                    orderGoods.setCount(goodsGspData.getCount()+"");
                    orderGoods.setGoodsCartId(goodsGspData.getGoodsCartId());

                    orderGoods.setStoreId(orderFormListData.getStoreId());
                    orderGoods.setStoreName(orderFormListData.getStoreName());
//                    if (goodsGspData.getDelivery()!=null){
//                        orderGoods.setDelivery(goodsGspData.getDelivery());
//                    }
                    orderGoods.setPer_count(""+goodsGspData.getCount());
                    orderGoods.setOrderId(""+orderFormListData.getId());
                    if (goodsGspData.getRefundStatus()!=null){
                        if (goodsGspData.getRefundStatus()==3){
                            orderGoods.setRefundStatus("已拒绝");
                        }else if (goodsGspData.getRefundStatus()==1){
                            orderGoods.setRefundStatus("退款中");
                        }else if (goodsGspData.getRefundStatus()==2){
                            orderGoods.setRefundStatus("退款成功");
                        }else if (goodsGspData.getRefundStatus()==0){
                            orderGoods.setRefundStatus("退款审核中");
                        }
                    }else {
                        orderGoods.setRefundStatus(null);
                    }

                    if (goodsGspData.getRefundType()!=null){
                        orderGoods.setRefundType(""+goodsGspData.getRefundType());
                    }else {
                        orderGoods.setRefundType(null);
                    }

                    if("0".equals(goodsInfoListData.getGoodsNumType())){
                        orderGoods.setType(OrderGoods.GOODS_TYPE.FUTURES);
                    }else if("1".equals(goodsInfoListData.getGoodsNumType())){
                        orderGoods.setType(OrderGoods.GOODS_TYPE.STOCK);
                    }
                    orderGoodsList.add(orderGoods);
                }
            }
            order.setGoodsList(orderGoodsList);
            order.setGoodsCount(goodsCount);
            switch (orderFormListData.getIsChangePrice()){
                case 0:
                    order.setTotalPrice(orderFormListData.getTotalPrice());
                    break;
                case 1:
                    //后台修改后的价格
                    order.setTotalPrice(orderFormListData.getRevisedPrice());
                    break;
            }
            order.setShipPrice(NumberUtils.getDoubleFromString(orderFormListData.getShipPrice()));
            order.setShipNo(orderFormListData.getShipCode());
            order.setOrderExplain(orderFormListData.getOrderExplain());
            orders.add(order);
        }
        return orders;
    }

}
