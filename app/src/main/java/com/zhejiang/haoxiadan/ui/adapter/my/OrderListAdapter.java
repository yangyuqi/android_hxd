package com.zhejiang.haoxiadan.ui.adapter.my;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.cart.DeleteOrderFormListener;
import com.zhejiang.haoxiadan.business.request.cart.OrderConfirmDelayListener;
import com.zhejiang.haoxiadan.business.request.cart.OrderSureReceiveListener;
import com.zhejiang.haoxiadan.business.request.cart.RemindDeliverListener;
import com.zhejiang.haoxiadan.business.request.my.OrderRequester;
import com.zhejiang.haoxiadan.db.GlobalDataUtil;
import com.zhejiang.haoxiadan.model.common.Order;
import com.zhejiang.haoxiadan.ui.activity.cart.OrderPaySelectActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.ShopActivity;
import com.zhejiang.haoxiadan.ui.activity.my.EvaluateActivity;
import com.zhejiang.haoxiadan.ui.activity.my.LogisticsActivity;
import com.zhejiang.haoxiadan.ui.activity.my.OrderDetailActivity;
import com.zhejiang.haoxiadan.ui.activity.my.RefundDetailActivity;
import com.zhejiang.haoxiadan.ui.adapter.AbsBaseAdapter;
import com.zhejiang.haoxiadan.ui.view.NoScrollListView;
import com.zhejiang.haoxiadan.ui.view.TipDialog;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.Event;
import com.zhejiang.haoxiadan.util.NumberUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 订单列表
 * Created by KK on 2017/8/26.
 */

public class OrderListAdapter extends AbsBaseAdapter<Order> {

    private boolean showStatus = false;

    //最多展示的商品
    private final int MAX_SHOW_GOODS = 6;

    private OrderRequester orderRequester;
    private RemindDeliverListenerImpl remindDeliverListener;
    private class RemindDeliverListenerImpl extends DefaultRequestListener implements RemindDeliverListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onRemindDeliverSuccess() {
            ToastUtil.show(mContext,R.string.tip_has_remind_ship);
        }
    }
    private OrderConfirmDelayListenerImpl orderConfirmDelayListener;
    private class OrderConfirmDelayListenerImpl extends DefaultRequestListener implements OrderConfirmDelayListener{

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onOrderConfirmDelaySuccess() {
            ToastUtil.show(mContext,R.string.tip_has_extend_receive);
        }
    }
    private OrderSureReceiveListenerImpl orderSureReceiveListener;
    private class OrderSureReceiveListenerImpl extends DefaultRequestListener implements OrderSureReceiveListener{

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onOrderSureReceiveSuccess() {
            EventBus.getDefault().post(Event.REFRESH_ORDER);
        }
    }
    private DeleteOrderFormListenerImpl deleteOrderFormListener;
    private class DeleteOrderFormListenerImpl extends DefaultRequestListener implements DeleteOrderFormListener{

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onDeleteOrderFormSuccess() {
            EventBus.getDefault().post(Event.REFRESH_ORDER);
        }
    }


    public OrderListAdapter(Context context, List<Order> datas) {
        super(context, datas);
        orderRequester = new OrderRequester();
        remindDeliverListener = new RemindDeliverListenerImpl();
        orderConfirmDelayListener = new OrderConfirmDelayListenerImpl();
        orderSureReceiveListener = new OrderSureReceiveListenerImpl();
        deleteOrderFormListener = new DeleteOrderFormListenerImpl();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_order_list, null);

            holder.supplierNameTv = (TextView) convertView.findViewById(R.id.tv_supplier);
            holder.orderStatusTv = (TextView) convertView.findViewById(R.id.tv_order_status);
            holder.moreLl = (LinearLayout) convertView.findViewById(R.id.ll_more);
            holder.goodsCountTv = (TextView) convertView.findViewById(R.id.tv_goods_count);
            holder.totalPriceTv = (TextView) convertView.findViewById(R.id.tv_total_price);
            holder.shipPriceTv = (TextView) convertView.findViewById(R.id.tv_ship_price);
            holder.goodsLV = (NoScrollListView) convertView.findViewById(R.id.lv_goods);
            holder.remindShipTv = (TextView) convertView.findViewById(R.id.tv_remind_ship);
            holder.viewLogisticsTv = (TextView) convertView.findViewById(R.id.tv_view_logistics);
            holder.extendReceiveTv = (TextView) convertView.findViewById(R.id.tv_extend_receive);
            holder.deleteOrderTv = (TextView) convertView.findViewById(R.id.tv_delete_order);
            holder.inRefundTv = (TextView) convertView.findViewById(R.id.tv_in_refund);
            holder.sureReceiveTv = (TextView) convertView.findViewById(R.id.tv_sure_receive);
            holder.goEvaluateTv = (TextView) convertView.findViewById(R.id.tv_go_evaluate);
            holder.payTv = (TextView) convertView.findViewById(R.id.tv_pay);
            holder.ll_bottom = (LinearLayout) convertView.findViewById(R.id.ll_bottom);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.supplierNameTv.setText(mDatas.get(position).getSupplier().getCompany());

        OrderGoodsListAdapter adapter;
        if(mDatas.get(position).getGoodsList().size() > MAX_SHOW_GOODS){
            holder.moreLl.setVisibility(View.VISIBLE);
            adapter = new OrderGoodsListAdapter(mContext,mDatas.get(position).getGoodsList().subList(0,MAX_SHOW_GOODS),2);
        }else{
            holder.moreLl.setVisibility(View.GONE);
            adapter = new OrderGoodsListAdapter(mContext,mDatas.get(position).getGoodsList(),2);
        }
        holder.goodsLV.setAdapter(adapter);
        //屏蔽子list的焦点
        holder.goodsLV.setFocusable(false);
        holder.goodsLV.setClickable(false);
        holder.goodsLV.setPressed(false);
        holder.goodsLV.setEnabled(false);


        holder.goodsCountTv.setText(mDatas.get(position).getGoodsCount()+"");
        if (!String.valueOf(mDatas.get(position).getTotalPrice()).equals("0.0")) {
            holder.totalPriceTv.setText(NumberUtils.formatToDouble(mDatas.get(position).getTotalPrice() + ""));
        }else {
            double price = Double.parseDouble(mDatas.get(position).getGoodsList().get(0).getPrice())+mDatas.get(position).getShipPrice();
            holder.totalPriceTv.setText(NumberUtils.formatToDouble(price + ""));
        }
        holder.shipPriceTv.setText(NumberUtils.formatToDouble(mDatas.get(position).getShipPrice()+""));

        switch (mDatas.get(position).getOrderStatus()){
            case WAIT_PAY:
                holder.orderStatusTv.setText(R.string.label_order_wait_pay);
                holder.remindShipTv.setVisibility(View.GONE);
                holder.viewLogisticsTv.setVisibility(View.GONE);
                holder.extendReceiveTv.setVisibility(View.GONE);
                holder.deleteOrderTv.setVisibility(View.GONE);
                holder.inRefundTv.setVisibility(View.GONE);
                holder.sureReceiveTv.setVisibility(View.GONE);
                holder.goEvaluateTv.setVisibility(View.GONE);
                holder.payTv.setVisibility(View.VISIBLE);
                break;
            case WAIT_SHIP:
                holder.ll_bottom.setVisibility(View.GONE);
                holder.orderStatusTv.setText(R.string.label_order_wait_ship);
                holder.remindShipTv.setVisibility(View.VISIBLE);
                holder.viewLogisticsTv.setVisibility(View.GONE);
                holder.extendReceiveTv.setVisibility(View.GONE);
                holder.deleteOrderTv.setVisibility(View.GONE);
                holder.inRefundTv.setVisibility(View.GONE);
                holder.sureReceiveTv.setVisibility(View.GONE);
                holder.goEvaluateTv.setVisibility(View.GONE);
                holder.payTv.setVisibility(View.GONE);
                break;
            case SALER_REFUSE_ONE:
            case SALER_REFUSE_TWO:
                holder.orderStatusTv.setText(R.string.label_order_wait_ship);
                holder.remindShipTv.setVisibility(View.VISIBLE);
                holder.viewLogisticsTv.setVisibility(View.GONE);
                holder.extendReceiveTv.setVisibility(View.GONE);
                holder.deleteOrderTv.setVisibility(View.GONE);
                holder.inRefundTv.setVisibility(View.GONE);
                holder.sureReceiveTv.setVisibility(View.GONE);
                holder.goEvaluateTv.setVisibility(View.GONE);
                holder.payTv.setVisibility(View.GONE);
                break;
            case WAIT_RECEIVE:
                holder.ll_bottom.setVisibility(View.GONE);
                holder.orderStatusTv.setText(R.string.label_wait_receive);
                holder.remindShipTv.setVisibility(View.GONE);
                holder.viewLogisticsTv.setVisibility(View.VISIBLE);
                holder.extendReceiveTv.setVisibility(View.VISIBLE);
                holder.deleteOrderTv.setVisibility(View.GONE);
                holder.inRefundTv.setVisibility(View.GONE);
                holder.sureReceiveTv.setVisibility(View.VISIBLE);
                holder.goEvaluateTv.setVisibility(View.GONE);
                holder.payTv.setVisibility(View.GONE);
                break;
            case WAIT_EVALUATE:
                holder.orderStatusTv.setText(R.string.label_order_wait_evaluate);
                holder.remindShipTv.setVisibility(View.GONE);
                holder.viewLogisticsTv.setVisibility(View.GONE);
                holder.extendReceiveTv.setVisibility(View.GONE);
                holder.deleteOrderTv.setVisibility(View.VISIBLE);
                holder.inRefundTv.setVisibility(View.GONE);
                holder.sureReceiveTv.setVisibility(View.GONE);
                holder.goEvaluateTv.setVisibility(View.VISIBLE);
                holder.payTv.setVisibility(View.GONE);
                break;
            case COMPLETE:
                holder.orderStatusTv.setText(R.string.label_trade_complete);
                holder.remindShipTv.setVisibility(View.GONE);
                holder.viewLogisticsTv.setVisibility(View.GONE);
                holder.extendReceiveTv.setVisibility(View.GONE);
                holder.deleteOrderTv.setVisibility(View.VISIBLE);
                holder.inRefundTv.setVisibility(View.GONE);
                holder.sureReceiveTv.setVisibility(View.GONE);
                holder.goEvaluateTv.setVisibility(View.GONE);
                holder.payTv.setVisibility(View.GONE);
                break;
            case CANCEL:
                holder.orderStatusTv.setText(R.string.label_trade_close);
                holder.remindShipTv.setVisibility(View.GONE);
                holder.viewLogisticsTv.setVisibility(View.GONE);
                holder.extendReceiveTv.setVisibility(View.GONE);
                holder.deleteOrderTv.setVisibility(View.VISIBLE);
                holder.inRefundTv.setVisibility(View.GONE);
                holder.sureReceiveTv.setVisibility(View.GONE);
                holder.goEvaluateTv.setVisibility(View.GONE);
                holder.payTv.setVisibility(View.GONE);
                break;
            case APPLY_REFUND:
                holder.orderStatusTv.setText(R.string.label_in_refund);
                holder.remindShipTv.setVisibility(View.GONE);
                holder.viewLogisticsTv.setVisibility(View.GONE);
                holder.extendReceiveTv.setVisibility(View.GONE);
                holder.deleteOrderTv.setVisibility(View.GONE);
                holder.inRefundTv.setVisibility(View.VISIBLE);
                holder.sureReceiveTv.setVisibility(View.GONE);
                holder.goEvaluateTv.setVisibility(View.GONE);
                holder.payTv.setVisibility(View.GONE);
                holder.ll_bottom.setVisibility(View.GONE);
                break;
            case REFUND_ING:
                holder.orderStatusTv.setText(R.string.label_in_refund);
                holder.remindShipTv.setVisibility(View.GONE);
                holder.viewLogisticsTv.setVisibility(View.GONE);
                holder.extendReceiveTv.setVisibility(View.GONE);
                holder.deleteOrderTv.setVisibility(View.GONE);
                holder.inRefundTv.setVisibility(View.VISIBLE);
                holder.sureReceiveTv.setVisibility(View.GONE);
                holder.goEvaluateTv.setVisibility(View.GONE);
                holder.payTv.setVisibility(View.GONE);
                holder.ll_bottom.setVisibility(View.GONE);
                break;
            case REFUND_COMPLETE:
                holder.orderStatusTv.setText(R.string.label_trade_close);
                holder.remindShipTv.setVisibility(View.GONE);
                holder.viewLogisticsTv.setVisibility(View.GONE);
                holder.extendReceiveTv.setVisibility(View.GONE);
                holder.deleteOrderTv.setVisibility(View.GONE);
                holder.inRefundTv.setVisibility(View.GONE);
                holder.sureReceiveTv.setVisibility(View.GONE);
                holder.goEvaluateTv.setVisibility(View.GONE);
                holder.payTv.setVisibility(View.GONE);
                break;
            case END:
                holder.orderStatusTv.setText(R.string.label_trade_close);
                holder.remindShipTv.setVisibility(View.GONE);
                holder.viewLogisticsTv.setVisibility(View.GONE);
                holder.extendReceiveTv.setVisibility(View.GONE);
                holder.deleteOrderTv.setVisibility(View.VISIBLE);
                holder.inRefundTv.setVisibility(View.GONE);
                holder.sureReceiveTv.setVisibility(View.GONE);
                holder.goEvaluateTv.setVisibility(View.GONE);
                holder.payTv.setVisibility(View.GONE);
                break;
            default:
                holder.remindShipTv.setVisibility(View.GONE);
                holder.viewLogisticsTv.setVisibility(View.GONE);
                holder.extendReceiveTv.setVisibility(View.GONE);
                holder.deleteOrderTv.setVisibility(View.GONE);
                holder.inRefundTv.setVisibility(View.GONE);
                holder.sureReceiveTv.setVisibility(View.GONE);
                holder.goEvaluateTv.setVisibility(View.GONE);
                holder.payTv.setVisibility(View.GONE);
                break;
        }
        if(showStatus){
            holder.orderStatusTv.setVisibility(View.VISIBLE);
        }else{
            holder.orderStatusTv.setVisibility(View.GONE);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                intent.putExtra("orderId",mDatas.get(position).getId());
                mContext.startActivity(intent);
            }
        });
        holder.supplierNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(mContext, ShopActivity.class);
                intents.putExtra("storeId", mDatas.get(position).getSupplier().getId());
                mContext.startActivity(intents);
            }
        });


        holder.payTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //记录买的商品，（猜你喜欢用）
                    GlobalDataUtil.putObject(mContext, Constants.TEMP_DATA_KEY_BUY_GOODS_ID, mDatas.get(position).getGoodsList().get(0).getId());
                    Intent intent = new Intent(mContext, OrderPaySelectActivity.class);
                    intent.putExtra("orderId", mDatas.get(position).getId());
                    intent.putExtra("type", "");
                    mContext.startActivity(intent);
            }
        });
        holder.remindShipTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提醒发货
                orderRequester.remindDeliver(mContext,mDatas.get(position).getId(),remindDeliverListener);
            }
        });
        holder.extendReceiveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //延长收货
                orderRequester.orderConfirmDelay(mContext,mDatas.get(position).getId(),orderConfirmDelayListener);
            }
        });
        holder.viewLogisticsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看物流
                Intent intent = new Intent(mContext, LogisticsActivity.class);
                intent.putExtra("goodsIcon",mDatas.get(position).getGoodsList().get(0).getIcon());
                intent.putExtra("shipNo",mDatas.get(position).getShipNo());
                intent.putExtra("orderId",mDatas.get(position).getId());
                mContext.startActivity(intent);
            }
        });
        holder.sureReceiveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TipDialog tipDialog = new TipDialog(mContext, mContext.getString(R.string.tip), mContext.getString(R.string.tip_is_sure_receive), new TipDialog.OnTipDialogListener() {
                    @Override
                    public void onPositiveClick() {
                        //确认收货
                        orderRequester.orderSureReceive(mContext,mDatas.get(position).getId(),orderSureReceiveListener);
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
                tipDialog.show();
            }
        });
        holder.deleteOrderTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TipDialog tipDialog = new TipDialog(mContext, mContext.getString(R.string.tip), mContext.getString(R.string.tip_is_delete_order), new TipDialog.OnTipDialogListener() {
                    @Override
                    public void onPositiveClick() {
                        //删除订单
                        orderRequester.deleteOrderForm(mContext,mDatas.get(position).getId(),deleteOrderFormListener);
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
                tipDialog.show();
            }
        });
        holder.goEvaluateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去评价
                Intent intent = new Intent(mContext,EvaluateActivity.class);
                intent.putExtra("orderId",mDatas.get(position).getId());
                intent.putExtra("goodsId",mDatas.get(position).getGoodsList().get(0).getId());
                intent.putExtra("goodsIcon",mDatas.get(position).getGoodsList().get(0).getIcon());
                mContext.startActivity(intent);
            }
        });
        holder.inRefundTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退款中
                Intent intent = new Intent(mContext, RefundDetailActivity.class);
                intent.putExtra("orderId", mDatas.get(position).getId());
                intent.putExtra("goodsList", mDatas.get(position).getGoodsList());
                mContext.startActivity(intent);
            }
        });


        return convertView;
    }

    private class ViewHolder{
        TextView supplierNameTv;
        TextView orderStatusTv;
        LinearLayout moreLl ,ll_bottom;
        TextView goodsCountTv;
        TextView totalPriceTv;
        TextView shipPriceTv;
        NoScrollListView goodsLV;
        TextView remindShipTv;
        TextView viewLogisticsTv;
        TextView extendReceiveTv;
        TextView deleteOrderTv;
        TextView inRefundTv;
        TextView sureReceiveTv;
        TextView goEvaluateTv;
        TextView payTv;

    }

    public void setShowStatus(boolean showStatus){
        this.showStatus = showStatus;
    }

}
