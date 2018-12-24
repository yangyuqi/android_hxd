package com.zhejiang.haoxiadan.ui.activity.my;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.cart.CancelOrderListener;
import com.zhejiang.haoxiadan.business.request.cart.DeleteOrderFormListener;
import com.zhejiang.haoxiadan.business.request.cart.GetCancelOrderReasonListener;
import com.zhejiang.haoxiadan.business.request.cart.OrderConfirmDelayListener;
import com.zhejiang.haoxiadan.business.request.cart.OrderSureReceiveListener;
import com.zhejiang.haoxiadan.business.request.cart.RemindDeliverListener;
import com.zhejiang.haoxiadan.business.request.cart.SelectOrderFormByIdsListener;
import com.zhejiang.haoxiadan.business.request.chat.ChatRequester;
import com.zhejiang.haoxiadan.business.request.chat.GetUserRoleListener;
import com.zhejiang.haoxiadan.business.request.chat.QueryCustomerByStoreIdListener;
import com.zhejiang.haoxiadan.business.request.my.OrderRequester;
import com.zhejiang.haoxiadan.db.GlobalDataUtil;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.common.Order;
import com.zhejiang.haoxiadan.model.common.OrderGoods;
import com.zhejiang.haoxiadan.model.common.Reason;
import com.zhejiang.haoxiadan.model.common.UserRole;
import com.zhejiang.haoxiadan.model.requestData.out.chat.Customer;
import com.zhejiang.haoxiadan.third.jiguang.chat.activity.ChatActivity;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.activity.cart.OrderPaySelectActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.GoodsDetailsActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.ShopActivity;
import com.zhejiang.haoxiadan.ui.activity.common.LoginActivity;
import com.zhejiang.haoxiadan.ui.adapter.my.OrderGoodsListAdapter;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;
import com.zhejiang.haoxiadan.ui.view.NoScrollListView;
import com.zhejiang.haoxiadan.ui.view.TipDialog;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.Event;
import com.zhejiang.haoxiadan.util.NumberUtils;
import com.zhejiang.haoxiadan.util.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailActivity extends BaseActivity {

    LinearLayout llBottom;
    private CommonTitle commonTitle;
    private TextView remindShipTv;
    private TextView viewLogisticsTv;
    private TextView extendReceiveTv;
    private TextView deleteOrderTv;
    private TextView cancelOrderTv;
    private TextView inRefundTv;
    private TextView refundTv;
    private TextView sureReceiveTv;
    private TextView goEvaluateTv;
    private TextView payTv;

    private TextView orderStatusTv;
    private TextView endTimeTv;
    private ImageView orderStatusIv;

    private TextView nameTv;
    private TextView mobileTv;
    private TextView addressTv;
    private TextView messageTv;
    private TextView supplierTv;

    private NoScrollListView listView;
    private TextView totalGoodsPriceTv;
    private TextView shipPriceTv;
    private TextView needPayLabelTv;
    private TextView needPayTv;
    private LinearLayout connectSalerLl;
    private LinearLayout callPhoneLl;

    private LinearLayout orderNoLl;
    private TextView orderNoTv;
    private LinearLayout orderTimeLl;
    private TextView orderTimeTv;
    private LinearLayout payTimeLl;
    private TextView payTimeTv;
    private LinearLayout payWayLl;
    private TextView payWayTv;
    private LinearLayout shipTimeLl;
    private TextView shipTimeTv;
    private TextView copyTv;

    private OptionsPickerView pickerView;
    private List<Reason> reasons;

    private OrderGoodsListAdapter adapter;
    private ArrayList<OrderGoods> orderGoodsList;

    private String orderId;
    private Order mOrder;

    private ClipboardManager clipboardManager;

    private ChatRequester chatRequester;
    private OrderRequester orderRequester;
    private SelectOrderFormByIdsListenerImpl selectOrderFormByIdsListener;

    private class SelectOrderFormByIdsListenerImpl extends DefaultRequestListener implements SelectOrderFormByIdsListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onSelectOrderFormByIdsSuccess(Order order) {
            mOrder = order;
            refreshView();
        }
    }

    private OrderSureReceiveListenerImpl orderSureReceiveListener;

    private class OrderSureReceiveListenerImpl extends DefaultRequestListener implements OrderSureReceiveListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onOrderSureReceiveSuccess() {
            EventBus.getDefault().post(Event.REFRESH_ORDER);
        }
    }

    private DeleteOrderFormListenerImpl deleteOrderFormListener;

    private class DeleteOrderFormListenerImpl extends DefaultRequestListener implements DeleteOrderFormListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onDeleteOrderFormSuccess() {
            EventBus.getDefault().post(Event.REFRESH_ORDER);
            finish();
        }
    }

    private OrderConfirmDelayListenerImpl orderConfirmDelayListener;

    private class OrderConfirmDelayListenerImpl extends DefaultRequestListener implements OrderConfirmDelayListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onOrderConfirmDelaySuccess() {
            ToastUtil.show(OrderDetailActivity.this, R.string.tip_has_extend_receive);
        }
    }

    private RemindDeliverListenerImpl remindDeliverListener;

    private class RemindDeliverListenerImpl extends DefaultRequestListener implements RemindDeliverListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onRemindDeliverSuccess() {
            ToastUtil.show(OrderDetailActivity.this, R.string.tip_has_remind_ship);
        }
    }

    private CancelOrderListenerImpl cancelOrderListener;

    private class CancelOrderListenerImpl extends DefaultRequestListener implements CancelOrderListener {

        @Override
        public void onCancelOrderSuccess() {
            EventBus.getDefault().post(Event.REFRESH_ORDER);
        }

        @Override
        protected void onRequestFail() {

        }
    }

    private GetCancelOrderReasonListenerImpl getCancelOrderReasonListener;

    private class GetCancelOrderReasonListenerImpl extends DefaultRequestListener implements GetCancelOrderReasonListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onGetCancelOrderReasonSuccess(List<Reason> reasonList) {
            reasons = reasonList;
            pickerView.setPicker(reasons);
        }
    }

    private GetUserRoleListenerImpl getUserRoleListenerImpl = new GetUserRoleListenerImpl();

    private class GetUserRoleListenerImpl extends DefaultRequestListener implements GetUserRoleListener {

        @Override
        protected void onRequestFail() {
        }

        @Override
        public void onGetUserRoleSuccess(UserRole userRole) {
            SharedPreferencesUtil.put(OrderDetailActivity.this, Constants.userRole, userRole.getUserRole());
            switch (userRole.getUserRole()) {
//                case Constants.USER_CUSTOMER:
//                    ToastUtil.show(OrderDetailActivity.this, R.string.tip_only_purchaser_can_chat);
//                    break;
                case Constants.USER_CUSTOMER:
                case Constants.USER_BUY:
                    chatRequester.queryCustomerByStoreId(OrderDetailActivity.this, mOrder.getSupplier().getId(), (String) SharedPreferencesUtil.get(OrderDetailActivity.this, Constants.userName, ""), queryCustomerByStoreIdImpl);
                    break;
//                case Constants.USER_CUSTOMER:
//                    ToastUtil.show(OrderDetailActivity.this, R.string.tip_only_purchaser_can_chat);
//                    break;
//                case Constants.USER_CUSTOMER:
//                    ToastUtil.show(OrderDetailActivity.this, R.string.tip_only_purchaser_can_chat);
//                    break;
            }

        }
    }

    private QueryCustomerByStoreIdListenerImpl queryCustomerByStoreIdImpl = new QueryCustomerByStoreIdListenerImpl();

    private class QueryCustomerByStoreIdListenerImpl extends DefaultRequestListener implements QueryCustomerByStoreIdListener {

        @Override
        protected void onRequestFail() {
        }

        @Override
        public void onQueryCustomerSuccess(Customer customer) {
            Intent intent = new Intent();
            intent.putExtra(Constants.CONV_TITLE, customer.getStoreName());
            intent.putExtra(Constants.TARGET_ID, customer.getAdminId());
            intent.putExtra(Constants.TARGET_APP_KEY, Constants.JPUSH_APPKEY);
            intent.putExtra("storeId", NumberUtils.getIntFromString(customer.getStoreId()) + "");
            String nickName = (String) SharedPreferencesUtil.get(OrderDetailActivity.this, Constants.nickName, "");
            if (nickName != null && !"".equals(nickName)) {
                intent.putExtra("buyName", nickName);
            } else {
                intent.putExtra("buyName", (String) SharedPreferencesUtil.get(OrderDetailActivity.this, Constants.userName, ""));
            }
            intent.putExtra("buyImg", (String) SharedPreferencesUtil.get(OrderDetailActivity.this, Constants.user_icon, ""));
            intent.putExtra("buyId", (String) SharedPreferencesUtil.get(OrderDetailActivity.this, Constants.userName, ""));
            intent.putExtra("storeName", customer.getStoreName());
            intent.putExtra("storeLogo", customer.getStoreLogo());
            intent.putExtra("customerPhone", customer.getCustomerPhone());
            intent.putExtra("storePhone", customer.getStorePhone());
            if (
                    (customer.getAdminId() == null || "".equals(customer.getAdminId())) ||
                            (customer.getStoreId() == null || "".equals(customer.getStoreId())) ||
                            (customer.getStorePhone() == null)
                    ) {
                ToastUtil.show(OrderDetailActivity.this, R.string.chat_no_store_msg);
                return;
            }
            intent.setClass(OrderDetailActivity.this, ChatActivity.class);
            startActivity(intent);
        }
    }

    //倒计时
    private final int MSG_COUNT_DOWN = 0x0001;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_COUNT_DOWN:
                    if (mOrder != null && mOrder.getTogetherEndMillis() > 1000) {
                        mOrder.setTogetherEndMillis(mOrder.getTogetherEndMillis() - 1000);
                        endTimeTv.setText(TimeUtils.getTimeDiff(mOrder.getTogetherEndMillis()));
                        handler.sendEmptyMessageDelayed(MSG_COUNT_DOWN, 1000);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        EventBus.getDefault().register(this);

        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        chatRequester = new ChatRequester();
        orderRequester = new OrderRequester();
        selectOrderFormByIdsListener = new SelectOrderFormByIdsListenerImpl();
        orderSureReceiveListener = new OrderSureReceiveListenerImpl();
        deleteOrderFormListener = new DeleteOrderFormListenerImpl();
        orderConfirmDelayListener = new OrderConfirmDelayListenerImpl();
        remindDeliverListener = new RemindDeliverListenerImpl();
        cancelOrderListener = new CancelOrderListenerImpl();
        getCancelOrderReasonListener = new GetCancelOrderReasonListenerImpl();

        orderId = getIntent().getStringExtra("orderId");
        if (orderId == null) {
            finish();
            return;
        }

        initView();
        initEvent();
        initData();
    }

    private void initView() {
        commonTitle = (CommonTitle) findViewById(R.id.common_title);
        remindShipTv = (TextView) findViewById(R.id.tv_remind_ship);
        viewLogisticsTv = (TextView) findViewById(R.id.tv_view_logistics);
        extendReceiveTv = (TextView) findViewById(R.id.tv_extend_receive);
        deleteOrderTv = (TextView) findViewById(R.id.tv_delete_order);
        cancelOrderTv = (TextView) findViewById(R.id.tv_cancel_order);
        inRefundTv = (TextView) findViewById(R.id.tv_in_refund);
        refundTv = (TextView) findViewById(R.id.tv_refund);
        sureReceiveTv = (TextView) findViewById(R.id.tv_sure_receive);
        goEvaluateTv = (TextView) findViewById(R.id.tv_go_evaluate);
        payTv = (TextView) findViewById(R.id.tv_pay);
        llBottom = (LinearLayout) findViewById(R.id.ll_bottom);
        orderStatusTv = (TextView) findViewById(R.id.tv_status);
        endTimeTv = (TextView) findViewById(R.id.tv_end_time);
        orderStatusIv = (ImageView) findViewById(R.id.iv_status);

        nameTv = (TextView) findViewById(R.id.tv_name);
        mobileTv = (TextView) findViewById(R.id.tv_mobile);
        addressTv = (TextView) findViewById(R.id.tv_address);
        messageTv = (TextView) findViewById(R.id.tv_message);
        supplierTv = (TextView) findViewById(R.id.tv_supplier);

        listView = (NoScrollListView) findViewById(R.id.lv_goods);
        totalGoodsPriceTv = (TextView) findViewById(R.id.tv_total_goods_price);
        shipPriceTv = (TextView) findViewById(R.id.tv_ship_price);
        needPayTv = (TextView) findViewById(R.id.tv_need_pay);
        needPayLabelTv = (TextView) findViewById(R.id.tv_need_pay_label);
        connectSalerLl = (LinearLayout) findViewById(R.id.ll_connect_saler);
        callPhoneLl = (LinearLayout) findViewById(R.id.ll_call_phone);

        orderNoLl = (LinearLayout) findViewById(R.id.ll_order_no);
        orderNoTv = (TextView) findViewById(R.id.tv_order_no);
        orderTimeLl = (LinearLayout) findViewById(R.id.ll_order_time);
        orderTimeTv = (TextView) findViewById(R.id.tv_order_time);
        payTimeLl = (LinearLayout) findViewById(R.id.ll_pay_time);
        payTimeTv = (TextView) findViewById(R.id.tv_pay_time);
        payWayLl = (LinearLayout) findViewById(R.id.ll_pay_way);
        payWayTv = (TextView) findViewById(R.id.tv_pay_way);
        shipTimeLl = (LinearLayout) findViewById(R.id.ll_ship_time);
        shipTimeTv = (TextView) findViewById(R.id.tv_ship_time);
        copyTv = (TextView) findViewById(R.id.tv_copy);

        orderGoodsList = new ArrayList<>();
        adapter = new OrderGoodsListAdapter(this, orderGoodsList, 1);
        listView.setAdapter(adapter);
    }

    private void initEvent() {
        commonTitle.setOnTitleClickListener(new CommonTitle.OnTitleClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
        supplierTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(OrderDetailActivity.this, ShopActivity.class);
                intents.putExtra("storeId", mOrder.getSupplier().getId());
                startActivity(intents);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OrderDetailActivity.this, GoodsDetailsActivity.class);
                intent.putExtra("goodsId", orderGoodsList.get(position).getId());
                startActivity(intent);
            }
        });
        cancelOrderTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerView.show();
            }
        });
        payTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //记录买的商品，（猜你喜欢用）
                GlobalDataUtil.putObject(OrderDetailActivity.this, Constants.TEMP_DATA_KEY_BUY_GOODS_ID, mOrder.getGoodsList().get(0).getId());
                //付款
                Intent intent = new Intent(OrderDetailActivity.this, OrderPaySelectActivity.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("type", "");
                startActivity(intent);
            }
        });
        remindShipTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提醒发货
                orderRequester.remindDeliver(OrderDetailActivity.this, orderId, remindDeliverListener);
            }
        });
        refundTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOrder.getOrderStatus() == Order.ORDER_STATUS.SALER_REFUSE_TWO) {
                    ToastUtil.show(OrderDetailActivity.this, R.string.tip_refund_times_full);
                    return;
                }

                //退款
                Intent intent = new Intent(OrderDetailActivity.this, ApplyRefundActivity.class);
                intent.putExtra("orderId", orderId);
                if (mOrder.getOrderStatus() == Order.ORDER_STATUS.SALER_REFUSE_ONE) {
                    intent.putExtra("needPic", true);
                }
                startActivity(intent);
            }
        });
        inRefundTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退款中
                Intent intent = new Intent(OrderDetailActivity.this, RefundDetailActivity.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("goodsList", orderGoodsList);
                startActivity(intent);
            }
        });
        extendReceiveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //延长收货
                orderRequester.orderConfirmDelay(OrderDetailActivity.this, orderId, orderConfirmDelayListener);
            }
        });
        viewLogisticsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看物流
                Intent intent = new Intent(OrderDetailActivity.this, LogisticsActivity.class);
                intent.putExtra("goodsIcon", mOrder.getGoodsList().get(0).getIcon());
                intent.putExtra("shipNo", mOrder.getShipNo());
                intent.putExtra("orderId", orderId);
                startActivity(intent);
            }
        });
        sureReceiveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!sureReceiveTv.getText().toString().equals("发货信息")) {

                    //确认收货
                    TipDialog tipDialog = new TipDialog(OrderDetailActivity.this, getString(R.string.tip), getString(R.string.tip_is_sure_receive), new TipDialog.OnTipDialogListener() {
                        @Override
                        public void onPositiveClick() {
                            //确认收货
                            orderRequester.orderSureReceive(OrderDetailActivity.this, orderId, orderSureReceiveListener);
                        }

                        @Override
                        public void onNegativeClick() {

                        }
                    });
                    tipDialog.show();
                }else {
                    Intent intent = new Intent(OrderDetailActivity.this,DeliveryGoodsActivity.class);
                    intent.putExtra("goods",mOrder.getGoodsList());
                    OrderDetailActivity.this.startActivity(intent);
                }
            }
        });
        goEvaluateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去评价
                Intent intent = new Intent(OrderDetailActivity.this, EvaluateActivity.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("goodsId", mOrder.getGoodsList().get(0).getId());
                intent.putExtra("goodsIcon", mOrder.getGoodsList().get(0).getIcon());
                startActivity(intent);
            }
        });
        deleteOrderTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TipDialog tipDialog = new TipDialog(OrderDetailActivity.this, getString(R.string.tip), getString(R.string.tip_is_delete_order), new TipDialog.OnTipDialogListener() {
                    @Override
                    public void onPositiveClick() {
                        //删除订单
                        orderRequester.deleteOrderForm(OrderDetailActivity.this, orderId, deleteOrderFormListener);
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
                tipDialog.show();
            }
        });

        pickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (reasons != null) {
                    //取消订单
                    orderRequester.cancelOrder(OrderDetailActivity.this, orderId, reasons.get(options1).getTitle(), cancelOrderListener);
                }
            }
        }).setTitleText(getString(R.string.label_choose_cancel_reason)).build();
        copyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 将文本内容放到系统剪贴板里。
                clipboardManager.setText(mOrder.getOrderNo());
                ToastUtil.show(OrderDetailActivity.this, R.string.tip_has_copy_success);
            }
        });
        connectSalerLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"".equals(getAccessToken())) {
                    String userType2 = (String) SharedPreferencesUtil.get(OrderDetailActivity.this, Constants.userRole, "");
//                    switch (userType2) {
//                        case Constants.USER_REGULAR:
//                            //查看用户权限
//                            chatRequester.getUserRole(OrderDetailActivity.this, getAccessToken(), getUserRoleListenerImpl);
//                            break;
//                        case Constants.USER_CUSTOMER:
//                        case Constants.USER_SELL:
//                        case Constants.USER_BUY:
                    chatRequester.queryCustomerByStoreId(OrderDetailActivity.this, mOrder.getSupplier().getId(), (String) SharedPreferencesUtil.get(OrderDetailActivity.this, Constants.userName, ""), queryCustomerByStoreIdImpl);
//                            break;
//                        case Constants.USER_SELL:
//                            ToastUtil.show(OrderDetailActivity.this, "商家禁止聊天");
//                            break;
//                        case Constants.USER_CUSTOMER:
//                            ToastUtil.show(OrderDetailActivity.this, "客服禁止聊天");
//                            break;
//                    }
                } else {
                    startActivity(new Intent(OrderDetailActivity.this, LoginActivity.class));
                }
            }
        });
        callPhoneLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(mOrder.getSupplier().getContactPhone());
            }
        });
    }

    private void initData() {
        requestData();

        requestCancelReason();
    }

    private void requestData() {
        orderRequester.selectOrderFormByIds(this, getAccessToken(), orderId, selectOrderFormByIdsListener);
    }

    private void requestCancelReason() {
        orderRequester.getCancelOrderReason(this, getCancelOrderReasonListener);
    }

    private void refreshView() {
        switch (mOrder.getOrderStatus()) {
            case WAIT_PAY:
                orderStatusTv.setText(R.string.label_order_wait_buyer_pay);
//                endTimeTv.setText(TimeUtils.getTimeDiff(mOrder.getTogetherEndMillis()));
                endTimeTv.setText("");
                needPayLabelTv.setText(R.string.label_need_pay);
                orderStatusIv.setImageResource(R.mipmap.icon_order_pay);
                remindShipTv.setVisibility(View.GONE);
                viewLogisticsTv.setVisibility(View.GONE);
                extendReceiveTv.setVisibility(View.GONE);
                deleteOrderTv.setVisibility(View.GONE);
                cancelOrderTv.setVisibility(View.VISIBLE);
                inRefundTv.setVisibility(View.GONE);
                refundTv.setVisibility(View.GONE);
                sureReceiveTv.setVisibility(View.GONE);
                goEvaluateTv.setVisibility(View.GONE);
                payTv.setVisibility(View.VISIBLE);
                break;
            case WAIT_SHIP:
                orderStatusTv.setText(R.string.label_order_buyer_has_pay);
                endTimeTv.setText("");
                needPayLabelTv.setText(R.string.label_true_pay);
                orderStatusIv.setImageResource(R.mipmap.icon_order_send);
                remindShipTv.setVisibility(View.VISIBLE);
                viewLogisticsTv.setVisibility(View.GONE);
                extendReceiveTv.setVisibility(View.GONE);
                deleteOrderTv.setVisibility(View.GONE);
                cancelOrderTv.setVisibility(View.GONE);
                inRefundTv.setVisibility(View.GONE);
                refundTv.setVisibility(View.VISIBLE);
                sureReceiveTv.setVisibility(View.GONE);
                goEvaluateTv.setVisibility(View.GONE);
                payTv.setVisibility(View.GONE);
                payTimeLl.setVisibility(View.VISIBLE);
                payWayLl.setVisibility(View.VISIBLE);

                llBottom.setVisibility(View.GONE);

                break;
            case SALER_REFUSE_ONE:
                orderStatusTv.setText(R.string.label_saler_refuse_refund);
                endTimeTv.setText(getString(R.string.label_reason_) + mOrder.getRefundReason());
                needPayLabelTv.setText(R.string.label_true_pay);
                orderStatusIv.setImageResource(R.mipmap.icon_order_audit);
                remindShipTv.setVisibility(View.VISIBLE);
                viewLogisticsTv.setVisibility(View.GONE);
                extendReceiveTv.setVisibility(View.GONE);
                deleteOrderTv.setVisibility(View.GONE);
                cancelOrderTv.setVisibility(View.GONE);
                inRefundTv.setVisibility(View.GONE);
                refundTv.setVisibility(View.VISIBLE);
                sureReceiveTv.setVisibility(View.GONE);
                goEvaluateTv.setVisibility(View.GONE);
                payTv.setVisibility(View.GONE);

                payTimeLl.setVisibility(View.VISIBLE);
                payWayLl.setVisibility(View.VISIBLE);

                llBottom.setVisibility(View.GONE);
                break;
            case SALER_REFUSE_TWO:
                orderStatusTv.setText(R.string.label_saler_refuse_refund);
                endTimeTv.setText(getString(R.string.label_reason_) + mOrder.getRefundReason());
                needPayLabelTv.setText(R.string.label_true_pay);
                orderStatusIv.setImageResource(R.mipmap.icon_order_send);
                remindShipTv.setVisibility(View.VISIBLE);
                viewLogisticsTv.setVisibility(View.GONE);
                extendReceiveTv.setVisibility(View.GONE);
                deleteOrderTv.setVisibility(View.GONE);
                cancelOrderTv.setVisibility(View.GONE);
                inRefundTv.setVisibility(View.GONE);
                refundTv.setVisibility(View.VISIBLE);
                sureReceiveTv.setVisibility(View.GONE);
                goEvaluateTv.setVisibility(View.GONE);
                payTv.setVisibility(View.GONE);

                payTimeLl.setVisibility(View.VISIBLE);
                payWayLl.setVisibility(View.VISIBLE);
                break;
            case WAIT_RECEIVE:
                orderStatusTv.setText(R.string.label_order_wait_buyer_receive);
                endTimeTv.setText("");
                needPayLabelTv.setText(R.string.label_true_pay);
                orderStatusIv.setImageResource(R.mipmap.icon_order_take);
                remindShipTv.setVisibility(View.GONE);
//                viewLogisticsTv.setVisibility(View.VISIBLE);
//                extendReceiveTv.setVisibility(View.VISIBLE);

                viewLogisticsTv.setVisibility(View.GONE);
                extendReceiveTv.setVisibility(View.GONE);

                deleteOrderTv.setVisibility(View.GONE);
                cancelOrderTv.setVisibility(View.GONE);
                inRefundTv.setVisibility(View.GONE);
                refundTv.setVisibility(View.GONE);
                sureReceiveTv.setVisibility(View.VISIBLE);
                sureReceiveTv.setText("发货信息");
                goEvaluateTv.setVisibility(View.GONE);
                payTv.setVisibility(View.GONE);

                payTimeLl.setVisibility(View.VISIBLE);
                payWayLl.setVisibility(View.VISIBLE);
                shipTimeLl.setVisibility(View.VISIBLE);
                break;
            case WAIT_EVALUATE:
                orderStatusTv.setText(R.string.label_trade_success);
                endTimeTv.setText("");
                needPayLabelTv.setText(R.string.label_true_pay);
                orderStatusIv.setImageResource(R.mipmap.icon_order_evaluation);
                remindShipTv.setVisibility(View.GONE);
                viewLogisticsTv.setVisibility(View.GONE);
                extendReceiveTv.setVisibility(View.GONE);
                deleteOrderTv.setVisibility(View.VISIBLE);
                cancelOrderTv.setVisibility(View.GONE);
                inRefundTv.setVisibility(View.GONE);
                refundTv.setVisibility(View.GONE);
                sureReceiveTv.setVisibility(View.GONE);
                goEvaluateTv.setVisibility(View.VISIBLE);
                payTv.setVisibility(View.GONE);
                break;
            case COMPLETE:
                orderStatusTv.setText(R.string.label_trade_success);
                endTimeTv.setText("");
                needPayLabelTv.setText(R.string.label_true_pay);
                orderStatusIv.setImageResource(R.mipmap.icon_order_success);
                remindShipTv.setVisibility(View.GONE);
                viewLogisticsTv.setVisibility(View.GONE);
                extendReceiveTv.setVisibility(View.GONE);
                deleteOrderTv.setVisibility(View.VISIBLE);
                cancelOrderTv.setVisibility(View.GONE);
                inRefundTv.setVisibility(View.GONE);
                refundTv.setVisibility(View.GONE);
                sureReceiveTv.setVisibility(View.GONE);
                goEvaluateTv.setVisibility(View.GONE);
                payTv.setVisibility(View.GONE);
                break;
            case CANCEL:
                orderStatusTv.setText(R.string.label_trade_close);
                endTimeTv.setText("");
                needPayLabelTv.setText(R.string.label_true_pay);
                orderStatusIv.setImageResource(R.mipmap.icon_order_close);
                remindShipTv.setVisibility(View.GONE);
                viewLogisticsTv.setVisibility(View.GONE);
                extendReceiveTv.setVisibility(View.GONE);
                deleteOrderTv.setVisibility(View.VISIBLE);
                cancelOrderTv.setVisibility(View.GONE);
                inRefundTv.setVisibility(View.GONE);
                refundTv.setVisibility(View.GONE);
                sureReceiveTv.setVisibility(View.GONE);
                goEvaluateTv.setVisibility(View.GONE);
                payTv.setVisibility(View.GONE);
                break;
            case APPLY_REFUND:
                orderStatusTv.setText(R.string.label_saler_examine);
                endTimeTv.setText("");
                needPayLabelTv.setText(R.string.label_true_pay);
                orderStatusIv.setImageResource(R.mipmap.icon_order_audit);
                remindShipTv.setVisibility(View.GONE);
                viewLogisticsTv.setVisibility(View.GONE);
                extendReceiveTv.setVisibility(View.GONE);
                deleteOrderTv.setVisibility(View.GONE);
                cancelOrderTv.setVisibility(View.GONE);
                inRefundTv.setVisibility(View.VISIBLE);
                refundTv.setVisibility(View.GONE);
                sureReceiveTv.setVisibility(View.GONE);
                goEvaluateTv.setVisibility(View.GONE);
                payTv.setVisibility(View.GONE);

                payTimeLl.setVisibility(View.VISIBLE);
                payWayLl.setVisibility(View.VISIBLE);
                break;
            case REFUND_ING:
                orderStatusTv.setText(R.string.label_trade_close);
                endTimeTv.setText("");
                needPayLabelTv.setText(R.string.label_true_pay);
                orderStatusIv.setImageResource(R.mipmap.icon_order_close);
                remindShipTv.setVisibility(View.GONE);
                viewLogisticsTv.setVisibility(View.GONE);
                extendReceiveTv.setVisibility(View.GONE);
                deleteOrderTv.setVisibility(View.GONE);
                cancelOrderTv.setVisibility(View.GONE);
                inRefundTv.setVisibility(View.VISIBLE);
                refundTv.setVisibility(View.GONE);
                sureReceiveTv.setVisibility(View.GONE);
                goEvaluateTv.setVisibility(View.GONE);
                payTv.setVisibility(View.GONE);

                payTimeLl.setVisibility(View.VISIBLE);
                payWayLl.setVisibility(View.VISIBLE);
                break;
            case REFUND_COMPLETE:
                orderStatusTv.setText(R.string.label_trade_close);
                endTimeTv.setText("");
                needPayLabelTv.setText(R.string.label_true_pay);
                orderStatusIv.setImageResource(R.mipmap.icon_order_close);
                remindShipTv.setVisibility(View.GONE);
                viewLogisticsTv.setVisibility(View.GONE);
                extendReceiveTv.setVisibility(View.GONE);
                deleteOrderTv.setVisibility(View.GONE);
                cancelOrderTv.setVisibility(View.GONE);
                inRefundTv.setVisibility(View.GONE);
                refundTv.setVisibility(View.GONE);
                sureReceiveTv.setVisibility(View.GONE);
                goEvaluateTv.setVisibility(View.GONE);
                payTv.setVisibility(View.GONE);

                payTimeLl.setVisibility(View.VISIBLE);
                payWayLl.setVisibility(View.VISIBLE);
                break;
            case END:
                orderStatusTv.setText(R.string.label_not_group);
                endTimeTv.setText("");
                needPayLabelTv.setText(R.string.label_true_pay);
                orderStatusIv.setImageResource(R.mipmap.icon_order_wholesale);
                remindShipTv.setVisibility(View.GONE);
                viewLogisticsTv.setVisibility(View.GONE);
                extendReceiveTv.setVisibility(View.GONE);
                deleteOrderTv.setVisibility(View.VISIBLE);
                cancelOrderTv.setVisibility(View.GONE);
                inRefundTv.setVisibility(View.GONE);
                refundTv.setVisibility(View.GONE);
                sureReceiveTv.setVisibility(View.GONE);
                goEvaluateTv.setVisibility(View.GONE);
                payTv.setVisibility(View.GONE);

                payTimeLl.setVisibility(View.VISIBLE);
                payWayLl.setVisibility(View.VISIBLE);
                break;
            default:
                orderStatusTv.setText(R.string.label_trade_close);
                endTimeTv.setText("");
                needPayLabelTv.setText(R.string.label_true_pay);
                orderStatusIv.setImageResource(R.mipmap.icon_order_wholesale);
                remindShipTv.setVisibility(View.GONE);
                viewLogisticsTv.setVisibility(View.GONE);
                extendReceiveTv.setVisibility(View.GONE);
                deleteOrderTv.setVisibility(View.GONE);
                cancelOrderTv.setVisibility(View.GONE);
                inRefundTv.setVisibility(View.GONE);
                refundTv.setVisibility(View.GONE);
                sureReceiveTv.setVisibility(View.GONE);
                goEvaluateTv.setVisibility(View.GONE);
                payTv.setVisibility(View.GONE);

                payTimeLl.setVisibility(View.VISIBLE);
                payWayLl.setVisibility(View.VISIBLE);
                break;
        }

        nameTv.setText(getString(R.string.label_receive_people_) + mOrder.getAddress().getName());
        mobileTv.setText(mOrder.getAddress().getMobile());
        addressTv.setText(mOrder.getAddress().getArea() + mOrder.getAddress().getDetailAddress());
        messageTv.setText(mOrder.getMessage());
        supplierTv.setText(mOrder.getSupplier().getCompany());
        shipTimeTv.setText(mOrder.getShipTime());
        orderGoodsList.clear();
        orderGoodsList.addAll(mOrder.getGoodsList());
        adapter.notifyDataSetChanged();
        totalGoodsPriceTv.setText(getString(R.string.label_money) + NumberUtils.formatToDouble(mOrder.getTotalGoodsPrice() + ""));
        shipPriceTv.setText(getString(R.string.label_money) + NumberUtils.formatToDouble(mOrder.getShipPrice() + ""));
        needPayTv.setText(getString(R.string.label_money) + NumberUtils.formatToDouble(mOrder.getTotalPrice() + ""));


        if (!String.valueOf(mOrder.getTotalPrice()).equals("0.0")) {
            needPayTv.setText(getString(R.string.label_money) + NumberUtils.formatToDouble(mOrder.getTotalPrice() + ""));
        } else {
            double price = mOrder.getTotalGoodsPrice() + mOrder.getShipPrice();
            needPayTv.setText(NumberUtils.formatToDouble(price + ""));
        }


        orderNoTv.setText(mOrder.getOrderNo());
        orderTimeTv.setText(mOrder.getAddTime());
        payTimeTv.setText(mOrder.getPayTime());
        payWayTv.setText(mOrder.getPayType());

        handler.sendEmptyMessageDelayed(MSG_COUNT_DOWN, 1000);
    }

    //拨打电话
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    /**
     * 接收eventbus事件
     *
     * @param event
     */
    @Subscribe
    public void onEventMainThread(Event event) {
        switch (event) {
            case REFRESH_ORDER: //刷新订单
                requestData();
                break;
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (orderId != null) {
            EventBus.getDefault().post(orderId);
        }
    }
}
